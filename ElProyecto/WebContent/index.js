var a = 0;
var b = 0;

function login(){
    var json ={
            email: document.getElementById("emailL").value,
            contrasena: document.getElementById("passwordL").value
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
    fetch('./Login', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
            	//alert("todo bien login con exito");
            	document.location.replace(`http://localhost:8080/ElProyecto/home/home.html`)
                //localStorage.setItem('sesion', JSON.stringify(userData));
                localStorage.setItem('id', userData.id_usuario)
            }
     });
}

function registrar(){

	if(a == 1 && b == 1){
	    var json ={
	    		nombre: document.getElementById("nameR").value,
	    		apellido: document.getElementById("lastnameR").value,
	            email: document.getElementById("emailR").value,
	            contrasena: document.getElementById("passwordR").value
	    }
	    
	    
	    
	    let configs = {
	            method: 'post',
	            body: JSON.stringify(json),
	            withCredentials: true,
	            credentials: 'include',
	            headers: {
	                'Content-type': 'text'
	            }
	    }
	    
	    console.log(configs.body);
	    fetch('./Registro', configs)
	        .then(res => res.json())
	        .then(data => {console.log(data)
	        	let userData = data.userData;
	            if(data.status == 200){
	            	alert("todo bien registro con exito");
	            }
	            	else{
	            		console.log("no");
	            	}
	            	
	    });
	}
}

function checkEmail() {
    var email = document.getElementById('emailR');
    var mailFormat = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

    if (!mailFormat.test(email.value)) {
        alert('correo invalido');
        email.focus;
        return false;
    }else{
    	a = 1;
    }
}


const check = () => {
	checkEmail();
	checkPassword();
	registrar();
	
}
function checkPassword(){
    var password = document.getElementById('passwordR');
    var passwordFormat= /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;

    if (!passwordFormat.test(password.value)) {
        alert('La contrasena debe tener al menos 6 caracteres incluyendo: un numero, una letra mayuscula y una letra minuscula');
        password.focus;
        return false;
        console.log(aa);
    }else{
    	b = 1;
    }
}
