package dev.fumin.sample.eventdriven.presentation.api;

import com.google.gson.Gson;
import com.google.inject.Singleton;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.fumin.sample.eventdriven.application.usecase.project.CreateProjectUseCase;
import lombok.Data;
import lombok.Value;

@Singleton
@WebServlet(value = "/api/projects")
public class ProjectApi extends HttpServlet {

    @Inject
    private Gson gson;

    @Inject
    private CreateProjectUseCase useCase;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        ReqBody reqBody = gson.fromJson(body, ReqBody.class);

        String projectId = useCase.handle(reqBody.name);

        ResBody resBody = new ResBody(projectId);
        resp.getWriter().println(gson.toJson(resBody));
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Data
    public static class ReqBody {
        private String name;
    }

    @Value
    public static class ResBody {
        String projectId;
    }

}
