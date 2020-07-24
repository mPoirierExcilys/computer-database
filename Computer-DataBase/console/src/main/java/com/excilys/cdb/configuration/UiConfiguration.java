package com.excilys.cdb.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages= {"com.excilys.cdb.controllers", "com.excilys.cdb.ui"})
public class UiConfiguration {

}
