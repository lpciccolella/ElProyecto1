function crearArchivo(){
	var t = document.getElementById("nombreVideo").value
	userLogin = localStorage.getItem("id");
	var formData = new FormData();
	formData.append("file", document.getElementById("file").files[0]);
	myFile = document.getElementById("file").files[0].name;
	formData.append("id_usuario", userLogin);
	formData.append("nombre", t);
	formData.append("nombre_archivo", myFile);
	
	let configs = {
            method: 'post',
            body: formData,
            withCredentials: true,
            credentials: 'include'
    }
    fetch('../SubirArchivo', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
            	console.log("Todo bien");
            	window.location.reload(false);
            }else if(data.status == 409){
            	alert("El archivo ya existe");
            }
        });
	
}