package com.konglk.ims;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * Created by konglk on 2018/11/5.
 */
@SpringBootApplication
@Import({FdfsClientConfig.class})
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@EnableMongoRepositories("com.konglk.ims.dao.mongod")
public class ImsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ImsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ImsApplication.class, args);
    }
}
