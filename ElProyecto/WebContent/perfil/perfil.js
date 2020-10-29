var userLogin = localStorage.getItem("id");
console.log(userLogin)

function complete (){  
		let url = '../Usuario?id_usurio='+userLogin;
	    let configs = {
	            method: 'get',
	            withCredentials: true,
	            credentials: 'include',
	            headers: {
	                'Content-type': 'text'
	            }
	    }
	    
	    fetch(url, configs)
	        .then(res => res.json())
	        .then(data => {console.log(data)
	        	let userData = data.arrayBuscar;
	            if(data.status == 200){
	            	//alert("todo bien registro con exito");
	            	document.getElementById("name").value = data.nombre; 
	            	document.getElementById("lastname").value = data.apellido
	            	
	            	
	            }
	            	else{
	            		console.log("no funciona");
	            	}
	            	
	    });
}



function borrarUsuario(){
	    
	   	var  formData = new formData();
	    var userLogin = localStorage.getItem("id");
	    console.log("id user delete--"+userLogin)
	    formData.append("id_user", userLogin);
	    
	    
	    let configs = {
	            method: 'delete',
	            body: formData,
	            withCredentials: true,
	            credentials: 'include',
	            headers: {
	                'Content-type': 'text'
	            }
	    }
	    
	    fetch('../Usuario', configs)
	        .then(res => res.json())
	        .then(data => {console.log(data)
	        	let userData = data.userData;
	            if(data.status == 200){
	            	alert("todo bien registro con exito");
	            }
	            	else{
	            		console.log("no se registro con exito");
	            	}
	            	
	    });
}

function actualizarUsuario(){
    var json ={
    		nombre: document.getElementById("name").value,
    		apellido: document.getElementById("lastname").value,
            email: document.getElementById("email").value,
            id_usurio: userLogin
    }
    
    
    let configs = {
            method: 'put',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Usuario', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
          
            }else if(status == 409){
            	alert('El correo ya existe');
            }else if (status==500){
            	alert('No se pudo actualizar el usuario');
            }
     });
}

function cambiarClave(){
	let password1 = document.getElementById("password1").value;
	let password2 = document.getElementById("password2").value;
	if(password1 == password2){
		var json ={
	    		contrasena: document.getElementById("password1").value,
	    		id_usurio: userLogin
	  
	    }
	    
	    
	    let configs = {
	            method: 'post',
	            body: JSON.stringify(json),
	            withCredentials: true,
	            credentials: 'include',
	            headers: {
	                'Content-type': 'application/json'
	            }
	    }
	    fetch('../Usuario', configs)
	        .then(res => res.json())
	        .then(data => {console.log(data)
	        	let userData = data.userData;
	            if(data.status == 200){
	            }
	     });
	}else{
		alert("Las contrasenas son diferentes");
	}
	
}
	
