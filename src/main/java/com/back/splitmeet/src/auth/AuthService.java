package com.back.splitmeet.src.auth;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.back.splitmeet.src.auth.dto.ChkUserInfoRes;

import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	public String decryptBase64UrlToken(String idToken) {
		byte[] decode = new Base64UrlCodec().decode(idToken);
		return new String(decode, StandardCharsets.UTF_8);
	}

	public ChkUserInfoRes getUserInfo(String accesstoken) {
		ChkUserInfoRes chkUserInfoRes = new ChkUserInfoRes();
		return chkUserInfoRes;
	}
}
