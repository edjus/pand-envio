
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
  if (esRol('cliente')) {
    return 1
  }
}

const idRepartidorActual = () => {
  if (esRol('repartidor')) {
    return 2
  }
}

const getRestauranteIdLogueado = () => {
  if (esRol('duenio')) {
    return 1 // Soy el duenio del restaurante 1
  }
}

export { loguear, esRol, rolActual, idUsuarioActual, getRestauranteIdLogueado, idRepartidorActual }
