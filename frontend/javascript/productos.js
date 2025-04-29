function getproductos() {
    return new Promise(async (resolve) => {
        var url = "http://localhost:8080/api/v1//";
        var filtro = document.getElementById("nameFilter").value || "";
        if (filtro !== "") {
            url += "filter/" + filtro;
        }
        console.log(filtro);
        
        let headersList = {
            "Accept": "*/*",
            "User-Agent": "web",
            "Content-Type": "application/json"
        };

        let response = await fetch(url, {
            method: "GET",
            headers: headersList
        });

        if (!response.ok) {
            console.error("Error al obtener los autores:", response.status);
            return;
        }

        let data = await response.json();
        data.forEach(author => {
            
    });
    });
}