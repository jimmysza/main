document.addEventListener("DOMContentLoaded", () => {
    // Configuración del modal de agregar
    const addModal = document.querySelector(".modal-add")
    const addBtn = document.querySelector("#open-add-modal")
    const addCloseBtn = addModal?.querySelector(".close-modal")
  
    if (addBtn && addModal && addCloseBtn) {
      addBtn.addEventListener("click", (e) => {
        e.preventDefault()
        addModal.classList.add("show")
      })
  
      addCloseBtn.addEventListener("click", () => {
        addModal.classList.remove("show")
      })
    }
  
    // Configuración de los modales de editar
    const editModal = document.querySelector(".modal-edit")
    const editBtns = document.querySelectorAll(".btn_Edit button")
    const editCloseBtn = editModal?.querySelector(".close-modal")
  
    if (editModal && editCloseBtn) {
      // Para cada botón de editar
      editBtns.forEach((btn) => {
        btn.addEventListener("click", (e) => {
          e.preventDefault()
          e.stopPropagation() // Detener cualquier propagación del evento
  
          // Obtener la fila actual
          const row = btn.closest("tr")
  
          // Obtener los datos de la fila
          const idCliente = row.cells[0].textContent.trim()
          const nombreCompleto = row.cells[1].textContent.trim()
          const fechaNacimiento = row.cells[2].textContent.trim()
          const telefono = row.cells[3].textContent.trim()
          const email = row.cells[4].textContent.trim()
          const preferencias = row.cells[5].textContent.trim()
  
          // Llenar el formulario de edición
          const form = editModal.querySelector("form")
          const idInput = form.querySelector('input[type="number"]')
          if (idInput) idInput.value = idCliente
  
          const nombreInput = form.querySelector('input[type="text"]')
          if (nombreInput) nombreInput.value = nombreCompleto
  
          const fechaInput = form.querySelector('input[type="date"]')
          if (fechaInput) {
            // Convertir la fecha al formato yyyy-MM-dd si es necesario
            const fechaParts = fechaNacimiento.split("-")
            if (fechaParts.length === 3) {
              const formattedDate = `${fechaParts[0]}-${fechaParts[1].padStart(2, "0")}-${fechaParts[2].padStart(2, "0")}`
              fechaInput.value = formattedDate
            } else {
              fechaInput.value = fechaNacimiento
            }
          }
  
          const telefonoInput = form.querySelector('input[type="tel"]')
          if (telefonoInput) telefonoInput.value = telefono
  
          const emailInput = form.querySelector('input[type="email"]')
          if (emailInput) emailInput.value = email
  
          const preferenciaInput = form.querySelectorAll('input[type="text"]')[1]
          if (preferenciaInput) preferenciaInput.value = preferencias
  
          // Mostrar el modal usando la clase show
          editModal.classList.add("show")
        })
      })
  
      // Cerrar el modal de editar
      editCloseBtn.addEventListener("click", () => {
        editModal.classList.remove("show")
      })
    }
  
    // Configuración de los modales de eliminar
    const deleteModal = document.querySelector(".modal-delete")
    const deleteBtns = document.querySelectorAll(".btn_delete button")
    const deleteCloseBtn = deleteModal?.querySelector(".close-btn-modal")
  
    if (deleteModal && deleteCloseBtn) {
      // Para cada botón de eliminar
      deleteBtns.forEach((btn) => {
        btn.addEventListener("click", (e) => {
          e.preventDefault()
          e.stopPropagation() // Detener cualquier propagación del evento
  
          // Obtener el ID del cliente de la fila actual
          const row = btn.closest("tr")
          const idCliente = row.cells[0].textContent.trim()
  
          // Actualizar el enlace de eliminación con el ID correcto
          const deleteLink = deleteModal.querySelector("a")
          if (deleteLink) {
            deleteLink.href = `/cliente/eliminar/${idCliente}`
          }
  
          // Mostrar el modal usando la clase show
          deleteModal.classList.add("show")
        })
      })
  
      // Cerrar el modal de eliminar
      deleteCloseBtn.addEventListener("click", () => {
        deleteModal.classList.remove("show")
      })
    }
  
    // Cerrar modales al hacer clic fuera de ellos
    window.addEventListener("click", (e) => {
      if (e.target.classList.contains("modal")) {
        e.target.classList.remove("show")
      }
    })
  })
  