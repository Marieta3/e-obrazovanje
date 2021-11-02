import VueJwtDecode from "vue-jwt-decode";
export let server = 'localhost:9000'

export let protocol = 'http'

export function setJWTToken(jwt) {
  sessionStorage.setItem("JWT", JSON.stringify(jwt));
}

export function hasRole(role) {
  let jwt = JSON.parse(sessionStorage.getItem("JWT"));
  if (jwt == undefined || jwt == null || jwt == {}) {
    return false;
  }
  let decoded = VueJwtDecode.decode(jwt);
  return decoded.role.includes(role);
}

export function getJWTToken() {
  return JSON.parse(sessionStorage.getItem("JWT"));
}

export function getHeader() {
  if (getJWTToken()) {
    return {
      Authorization: "Bearer " + getJWTToken()
    };
  }
  return {
    Authorization: "Bearer "
  };
}

/*export function getLoggedUserID() {
  if (getJWTToken()) {
    return getJWTToken().profileId;
  }
  return 0;
}

export function isUserLogged() {
  return getLoggedUserID() != 0;
}

export function getLoggedUserUsername() {
  if (getJWTToken()) {
    return getJWTToken().username;
  }
  return null;
}

export function setLoggedUserUsername(u) {
  let jwt = getJWTToken();
  jwt.username = u;
  setJWTToken(jwt);
}*/

export function logOut() {
  sessionStorage.removeItem("JWT");
}

/*export function getUrlVars() {
  var vars = {};
  window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m, key, value) {
    vars[key] = value;
  });
  return vars;
}*/