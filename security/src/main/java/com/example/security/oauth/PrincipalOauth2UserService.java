package com.example.security.oauth;

import com.example.security.auth.PrincipalDetails;
import com.example.security.model.User;
import com.example.security.oauth.provider.GoogleUserInfo;
import com.example.security.oauth.provider.OAuth2UserInfo;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    //구글로부터 받은 userRequest데이터에 대해 후처리되는함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getAccessToken:" + userRequest.getAccessToken());
        System.out.println("getClientRegistration:" + userRequest.getClientRegistration());
        System.out.println("getRegistrationId:" + userRequest.getClientRegistration().getRegistrationId());
        System.out.println("getClientRegistration.getClientId:" + userRequest.getClientRegistration().getClientId());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes:" + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else{

        }

//        String provider = userRequest.getClientRegistration().getRegistrationId();
//        String providerId = oAuth2User.getAttribute("sub");
//        String username = provider+"_"+providerId;
//        String email = oAuth2User.getAttribute("email");

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;
        String email = oAuth2UserInfo.getEmail();

        User userEntity = userRepository.findByUsername(username);

        if(userEntity==null) {
            userEntity = User.builder()
                    .username(username)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
