package com.springml.salesforce.wave.util;

import static com.springml.salesforce.wave.util.WaveAPIConstants.*;

import java.net.URI;

import org.apache.log4j.Logger;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SFConfig {
    private static final Logger LOG = Logger.getLogger(HTTPHelper.class);

    private String username;
    private String password;
    private String loginURL;

    public SFConfig(String username, String password, String loginURL) {
        this.username = username;
        this.password = password;
        this.loginURL = loginURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }

    public PartnerConnection createPartnerConnection() throws Exception {
        ConnectorConfig config = new ConnectorConfig();
        LOG.debug("Connecting SF Partner Connection using " + username);
        config.setUsername(username);
        config.setPassword(password);
        String authEndpoint = getAuthEndpoint(loginURL);
        LOG.info("loginURL : " + authEndpoint);
        config.setAuthEndpoint(authEndpoint);
        config.setServiceEndpoint(authEndpoint);

        try {
            return Connector.newConnection(config);
        } catch (ConnectionException ce) {
            LOG.error("Exception while creating connection", ce);
            throw new Exception(ce);
        }
    }

    private String getAuthEndpoint(String loginURL) throws Exception {
        URI loginURI = new URI(loginURL);

        return new URI(loginURI.getScheme(), loginURI.getUserInfo(), loginURI.getHost(),
                loginURI.getPort(), PATH_SOAP_ENDPOINT, null, null).toString();
    }

}
