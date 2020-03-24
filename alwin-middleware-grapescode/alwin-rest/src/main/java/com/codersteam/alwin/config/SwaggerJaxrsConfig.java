package com.codersteam.alwin.config;

import io.swagger.jaxrs.config.BeanConfig;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "SwaggerJaxrsConfig", loadOnStartup = 1)
public class SwaggerJaxrsConfig extends HttpServlet {

    @Inject
    @Property("version.value")
    private String version;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("alwin-rest");
        beanConfig.setVersion(version);
        beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setBasePath("/alwin-rest");
        beanConfig.setResourcePackage("com.codersteam.alwin.rest");
        beanConfig.setScan(true);
    }
}