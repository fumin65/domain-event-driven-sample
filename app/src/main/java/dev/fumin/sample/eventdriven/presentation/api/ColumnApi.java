package dev.fumin.sample.eventdriven.presentation.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.fumin.sample.eventdriven.application.usecase.column.CreateColumnUseCase;
import lombok.Data;
import lombok.Value;

@Singleton
@WebServlet(value = "/api/columns")
public class ColumnApi extends HttpServlet {

    @Inject
    private Gson gson;

    @Inject
    private CreateColumnUseCase useCase;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        ReqBody reqBody = gson.fromJson(body, ReqBody.class);

        String columnId = useCase.handle(reqBody.projectId, reqBody.columnName);

        ResBody resBody = new ResBody(reqBody.projectId, columnId);
        resp.getWriter().println(gson.toJson(resBody));
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Data
    public static class ReqBody {
        private String projectId;
        private String columnName;
    }

    @Value
    public static class ResBody {
        String projectId;
        String columnId;
    }

}
