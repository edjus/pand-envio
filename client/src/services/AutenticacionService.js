
const loguear = (nuevoRol) => {
  localStorage.setItem('login', nuevoRol)
  location.reload()
}

const esRol = (rol) => {
  const curRol = localStorage.getItem('login')
  return curRol === rol
}

const rolActual = () => {
  return localStorage.getItem('login')
}

const idUsuarioActual = () => {
  return 1 // TODO se permite cambiar o todos los roles van a ser con el usuario id 1 ?
}

export { loguear, esRol, rolActual, idUsuarioActual }
