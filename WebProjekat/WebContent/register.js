var app = new Vue({
    el: '#registerForm',
    data:{
        korisnickoIme: '',
		lozinka: '',
		ime: '',
		prezime: '',
		pol: '',
		datumRodjenja: '',
    },
    methods: {
        registration: function(event){
            event.preventDefault()
            if(!this.ime || !this.lozinka || !this.ime || !this.prezime || !this.pol || !this.datumRodjenja){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
            axios.post('rest/users/register', 
            {"name": this.ime, "surname": this.prezime, "gender": this.pol,
            "dateOfBirth": this.datumRodjenja, "role": 'KUPAC', "username": this.korisnickoIme,
            "password": this.lozinka, "membershipFee": null, "visitedSportFacilities": null,
            "customerType": {"typeName": 'NEMA',"discount": 0.0, "requredNumberOfPoints": 1000},
            "points": 0})
            .then(response => {
                location.href=response.data 
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
        }
    }
})