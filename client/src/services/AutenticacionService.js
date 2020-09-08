
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

export { loguear, esRol, rolActual }
