package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private Environment env;

    public List<Credential> getAllCredentialsByUserId(Integer userId) {
        var res = credentialMapper.getCredentialsByUserId(userId);
//        if(res.isEmpty()) {
//            return List.of();
//        }
//        res.forEach(item -> {
//            item.setPassword(encryptionService.encryptValue(item.getPassword(),  env.getProperty("ENCRYPTION_KEY")));
//        });
        return res;
    }

    public void saveOrUpdate(Credential credential) {
        if (credential.getCredentialId() == null) {
            credentialMapper.insert(credential);
        } else {
            credentialMapper.update(credential);
        }
    }

    public void delete(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }
}
