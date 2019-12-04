package com.pccw.tyk.apitest;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pccw.tyk.apitest.utils.Json;
import com.pccw.tyk.apitest.utils.ListFactory;
import com.pccw.tyk.apitest.utils.MapFactory;

@RestController
public class Endpoint {

  @RequestMapping(value = "**", produces = "application/json")
  public String capture(HttpServletRequest request) {
    return requestAsJson(request);
  }

  private String requestAsJson(HttpServletRequest request) {
    return Json
      .map(
        MapFactory.entry("args", args(request)),
        MapFactory.entry("data", data(request)),
        MapFactory.entry("parts", files(request)),
        MapFactory.entry("headers", headers(request)),
        MapFactory.entry("method", method(request)),
        MapFactory.entry("origin", origin(request)),
        MapFactory.entry("url", url(request))
      )
      .get();
  }

  private Json args(HttpServletRequest request) {
    return Json
      .map(
        MapFactory
          .mapValue(
            request.getParameterMap(),
            vals -> Json.list(ListFactory.map(Json::quoteOrNull, ListFactory.from(vals)))
          )
      );
  }

  private Json data(HttpServletRequest request) {
    try (Reader reader = request.getReader()) {
      StringBuilder builder = new StringBuilder();
      for (int read = reader.read(); read != -1; read = reader.read())
        builder.append((char) read);
      return Json.quote(builder.toString());
    } catch (IOException | IllegalStateException e) {
      e.printStackTrace(System.out);
      return Json.quote("<error reading body>");
    }
  }

  private Json files(HttpServletRequest request) {
    try {
      return Json.list(ListFactory.from(part -> {
        return Json
          .map(
            MapFactory.entry("name", Json.quoteOrNull(part.getName())),
            MapFactory.entry("contentType", Json.quoteOrNull(part.getContentType())),
            MapFactory.entry("fileName", Json.quoteOrNull(part.getSubmittedFileName())),
            MapFactory
              .entry(
                "headers",
                Json
                  .map(
                    ListFactory
                      .from(
                        headerName -> MapFactory
                          .entry(
                            headerName,
                            Json.list(ListFactory.from(Json::quoteOrNull, part.getHeaders(headerName)))
                          ),
                        part.getHeaderNames()
                      )
                  )
              ),
            MapFactory.entry("size", Json.literal(part.getSize(), String::valueOf))
          );
      }, request.getParts()));
    } catch (IOException | IllegalStateException e) {
      e.printStackTrace(System.out);
      return Json.quote("<error reading files>");
    } catch (ServletException e) {
      e.printStackTrace(System.out);
      return Json.list();
    }
  }

  private Json headers(HttpServletRequest request) {
    return Json
      .map(
        ListFactory
          .from(
            headerName -> MapFactory
              .entry(headerName, Json.list(ListFactory.from(Json::quoteOrNull, request.getHeaders(headerName)))),
            request.getHeaderNames()
          )
      );
  }

  private Json method(HttpServletRequest request) {
    return Json.quoteOrNull(request.getMethod());
  }

  private Json origin(HttpServletRequest request) {
    return Json.quoteOrNull(request.getRemoteHost());
  }

  private Json url(HttpServletRequest request) {
    return Json.quoteOrNull(request.getRequestURL().toString());
  }
}
