SpringOAuth2Example

REST Endpoints added and checked so far

1. Ask for tokens[access+refresh] using HTTP POST on /oauth/token, with grant_type=password,and resource owners credentials as req-params. Additionally, send client credentials in Authorization header.
POST http://localhost:8080/SpringSecurityOAuth2Example/oauth/token?grant_type=password&username=bill&password=abc123

2. Ask for a new access token via valid refresh-token, using HTTP POST on /oauth/token, with grant_type=refresh_token,and sending actual refresh token. Additionally, send client credentials in Authorization header.
POST http://localhost:8080/SpringSecurityOAuth2Example/oauth/token?grant_type=refresh_token&refresh_token=045c3d23-973f-637c-43ed-1afd43ac925

3. Access the resource by providing an access token using access_token query param with request.
GET http://localhost:8080/SpringSecurityOAuth2Example/user/?access_token=094b7d23-973f-4cc1-83ad-8ffd43de1845
