
const loguear = (nuevoRol) => {
  localStorage.setItem('login', nuevoRol)
  location.reload()
}

const esRol = (rol) => {
  const rolActual = localStorage.getItem('login')
  return rolActual === rol
}

export { loguear, esRol }
