
const categorias = [{id: 1, nombre: 'ENTRADA'}, {id: 2, nombre: 'BEBIDA'}, {id: 3, nombre: 'PLATO'}, {id: 4, nombre: 'POSTRE'}]

const getCategorias = () => {
  return categorias
}
const getNombreCategoria = (id) => {
  const categoria = categorias.find(cat => cat.id === id)
  if (categoria) {
    return categoria.nombre
  }
}

const getCategoriaDesde = (nombre) => {
  return categorias.find(cat => cat.nombre === nombre.toUpperCase())
}
export {
  getCategorias,
  getNombreCategoria,
  getCategoriaDesde
}
