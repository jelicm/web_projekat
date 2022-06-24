var app = new Vue({
    el: '#registerForm',
    data:{
        korisnickoIme: '',
		lozinka: '',
		ime: '',
		prezime: '',
		pol: '',
		datumRodjenja: '',
		uloga: '',
    },
    methods: {
        registration: function(event){
            event.preventDefault()
            if(!this.korisnickoIme || !this.lozinka || !this.ime || !this.prezime || !this.pol || !this.datumRodjenja || !this.uloga){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
			if(this.uloga == 'TRENER'){
	            axios.post('rest/users/registerCoach', 
	            {"name": this.ime, "surname": this.prezime, "gender": this.pol,
	            "dateOfBirth": this.datumRodjenja, "role": this.uloga, "username": this.korisnickoIme,
	            "password": this.lozinka, "trainingHistories": null, "deleted": false})
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Korisnicko ime nije jedinstveno!")
            	})
            }
            else{
				axios.post('rest/users/registerManager', 
	            {"name": this.ime, "surname": this.prezime, "gender": this.pol,
	            "dateOfBirth": this.datumRodjenja, "role": this.uloga, "username": this.korisnickoIme,
	            "password": this.lozinka, "sportFacility": null, "deleted": false})
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Korisnicko ime nije jedinstveno!")
            	})
			}
        }
    }
})