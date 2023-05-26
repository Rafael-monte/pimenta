package com.example.sisave.configuration;

import com.example.sisave.services.SecurityTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Log4j2
@Component
public class IntegrationUpdate implements InitializingBean {
    @Autowired
    private SecurityTokenService service;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            log.info("Integrations: Executing token update routine.");
            if (this.service.needToUpdateToken()) {
                log.info("Integrations: Generating new integration token.");
                this.service.updateToken();
            }
        } catch(Exception e) {
            log.error("Occoured an error while execute a integration token routine.");
        }
    }




}
