package com.codersteam.alwin.core.service.template;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.ejb.Stateless;

import static java.util.Collections.singleton;

/**
 * @author Tomasz Sliwinski
 */
@Stateless
public class AlwinTemplateEngine {

    private final TemplateEngine templateEngine;

    public AlwinTemplateEngine() {
        templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
    }

    public String process(final String template, final IContext context) {
        return templateEngine.process(template, context);
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setResolvablePatterns(singleton("html/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
}
