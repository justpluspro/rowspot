package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.model.VersionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qwli7
 * @date 2021/2/9 8:24
 * 功能：VersionApi
 **/
@RestController
@RequestMapping("api")
public class VersionApi extends AbstractApi {

    @Value("${project.version}")
    private String version;

    @Value("${project.java.version}")
    private String javaVersion;

    @Value("${project.build.time}")
    private String buildTime;

    @GetMapping("version")
    public ResponseEntity<VersionInfo> getVersion(){
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setVersion(version);
        versionInfo.setUseJdkVersion(javaVersion);
        versionInfo.setBuildTime(buildTime);
        return ResponseEntity.ok(versionInfo);
    }
}
