
const loguear = (nuevoRol) => {
  localStorage.setItem('login', nuevoRol)
  location.reload()
}

const esRol = (roles) => {
  const curRol = localStorage.getItem('login')
  return roles.includes(curRol)
}

const rolActual = () => {
  return localStorage.getItem('login')
}

const idUsuarioActual = () => {
  return 1 // TODO se permite cambiar o todos los roles van a ser con el usuario id 1 ?
}

const getRestauranteIdLogueado = () => {
  if (esRol('duenio')) {
    return 1 // Soy el duenio del restaurante 1
  }
}

export { loguear, esRol, rolActual, idUsuarioActual, getRestauranteIdLogueado }
