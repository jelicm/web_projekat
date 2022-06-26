var app = new Vue({
    el: '#createSportFacility',
    data:{
        naziv: '',
		tip: '',
		geoDuzina: '',
		geoSirina: '',
		ulica: '',
		broj: '',
		grad: '',
		kod: '',
		slika: '',
		izabraniMenadzer: '',
		menadzeri: {},
		korisnickoIme: '',
		lozinka: '',
		ime: '',
		prezime: '',
		pol: '',
		datumRodjenja: ''
    },
    mounted(){
		axios.get('rest/users/availableManagers')
		.then(response => {this.menadzeri = response.data})
    },
    methods: {
        create: function(event){
            event.preventDefault()
            let array = this.slika.split("\\")
            let slikaPutanja = array[array.length - 1]
            if(!this.naziv || !this.tip || !this.geoDuzina || !this.geoSirina || !this.ulica || !this.broj || !this.grad
            	|| !this.kod || !slikaPutanja || !this.izabraniMenadzer){
				alert("Postoji nepopunjeno polje forme za sportski objekat!")
				return
			}
			axios.post('rest/sportFacilities/createSportFacility/' + this.izabraniMenadzer, 
	            {"name": this.naziv, "type": this.tip, "trainings": null, "status": "NE_RADI",
	            "location": {"longitude": this.geoDuzina, "latitude": this.geoSirina, "address": {"number" : this.broj,
	            "street" : this.ulica, "city" : this.grad, "zipCode" : this.kod} }, "image" : slikaPutanja, "averageRating" : 5.0,
	            "workTime" : "07:00 - 22:00", "deleted": false})
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Ime sportskog objekta nije jedinstveno!")
	            })	
        },
	    registerManager: function(){
			if(!this.korisnickoIme || !this.lozinka || !this.ime || !this.prezime || !this.pol || !this.datumRodjenja){
				alert("Postoji nepopunjeno polje forme za registrovanje menadzera!")
				return
			}
			axios.post('rest/users/registerManager', 
	            {"name": this.ime, "surname": this.prezime, "gender": this.pol,
	            "dateOfBirth": this.datumRodjenja, "role": "MENADZER", "username": this.korisnickoIme,
	            "password": this.lozinka, "sportFacility": null, "deleted": false})
	            .then(this.izabraniMenadzer = this.ime + " " + this.prezime)
	            .catch(function(){
	                alert("Korisnicko ime menadzera nije jedinstveno!")
            	})
		}
    }
})