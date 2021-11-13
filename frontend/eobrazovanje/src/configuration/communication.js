import VueJwtDecode from "vue-jwt-decode";
export let server = 'localhost:9000'

export let protocol = 'http'

export function setJWTToken(jwt) {
  sessionStorage.setItem("JWT", JSON.stringify(jwt));
}

export const Role = {
  ANON: 0,
  STUDENT: 1,
  TEACHER: 2,
  ADMIN: 3
};
Object.freeze(Role);

export function getRole() {
  let jwt = JSON.parse(sessionStorage.getItem("JWT"));
  if (jwt == undefined || jwt == null || jwt == {}) {
    return Role.ANON;
  }
  let decoded = VueJwtDecode.decode(jwt);
  switch (decoded.role) {
    case 'ROLE_STUDENT':
      return Role.STUDENT
    case 'ROLE_TEACHER':
      return Role.TEACHER
    case 'ROLE_ADMIN':
      return Role.ADMIN
    default:
      return Role.ANON
  }
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

export function hasAnyRole(...roles) {
  let loggedUserRole = getRole()
  for (let role of roles) {
    if (role == loggedUserRole) {
      return true
    }
  }
  return false
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