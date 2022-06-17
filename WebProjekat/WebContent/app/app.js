var app = new Vue({
    el: '#loginForm',
    data:{
        korisnickoIme: '',
		lozinka: '',
		objekti: {},
		pretrazeniObjekti: {},
		naziv: '',
		tip: '',
		lokacija: '',
		prosecnaOcena: ''
    },

	mounted() {
		axios
			.get("rest/sportFacilities")
			.then((response) => {
				this.objekti = response.data;
				this.pretrazeniObjekti = response.data;	
			});
	},
	
    methods: {
        login: function(event){
            event.preventDefault()
            if(!this.korisnickoIme || !this.lozinka){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
            axios.get('rest/users/login/' + this.korisnickoIme + '/' + this.lozinka)
            .then(response => {
                location.href=response.data 
            })
            .catch(function(){
                alert("Neispravni kredencijali!")
            })
        },
		pretragaNaziv: function(naziv){
			this.pretrazeniObjekti = this.objekti.filter(o => o.name.toLowerCase().includes(naziv.toLowerCase()));
			
			this.prosecnaOcena = '';
			this.tip = '';
			this.lokacija = '';
		
		},
		pretragaLokacija: function(lokacija){
			this.pretrazeniObjekti = this.objekti.filter(o => (o.location.address.street.toLowerCase().includes(lokacija.toLowerCase()) || o.location.address.city.toLowerCase().includes(lokacija.toLowerCase())));
			this.naziv = '';
			this.prosecnaOcena = '';
			this.tip = '';
			
		},
		pretragaOcena: function(prosecnaOcena){
			this.pretrazeniObjekti = this.objekti.filter(o => o.averageRating == prosecnaOcena);
			this.naziv = '';
			this.tip = '';
			this.lokacija = '';
		},
		pretragaTip: function(tip){
			this.pretrazeniObjekti = this.objekti.filter(o => o.type.toLowerCase().replace('_', '').includes(tip.replace(' ','').toLowerCase()));
			this.naziv = '';
			this.prosecnaOcena = '';
			this.lokacija = '';
		},
		ponistiPretragu: function(){
			this.pretrazeniObjekti = this.objekti;
			this.naziv = '';
			this.prosecnaOcena = '';
			this.tip = '';
			this.lokacija = '';
		}
    }
})