function cerrar(){
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Logout', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
            	//alert("Sesion cerrada");
            	document.location.replace(`http://localhost:8080/Youtube`)
                //localStorage.setItem('sesion', JSON.stringify(userData));
                localStorage.setItem('id', JSON.stringify(userData.id_usuario))
            }
     });
}