var module = require('main_module');

function Service ($cookies, jwtHelper) {

    this.getSession = getSession;
    this.storeToken = storeToken;
    this.getToken = getToken;
    this.invalidate = invalidate;
    this.isSessionActive = isSessionActive;
    this.getRole = getRole;
    this.getUsername = getUsername;

    function storeToken(token) {
        $cookies.put("access_token", token);
    }

    function getToken() {
        return $cookies.get("access_token");
    }

    function getSession() {
        var token = getToken();
        if (token) {
            return jwtHelper.decodeToken(token);
        } else {
            return undefined;
        }
    }

    function isSessionActive() {
        var token = getToken();
        return token && !jwtHelper.isTokenExpired(token);
    }

    function invalidate() {
        return $cookies.remove("access_token");
    }

     function getRole() {
      var accessToken =  getSession();
      if (accessToken) {
        return accessToken.authorities;
      }
      return [];
    }

    function getUsername() {
      var accessToken =  getSession();
      if (accessToken) {
        return accessToken.user_name;
      }
      return '';
    }
}

Service.$inject = ['$cookies', 'jwtHelper'];
module.service('Session', Service);
