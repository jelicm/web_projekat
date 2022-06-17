var app = new Vue({
    el: '#loginForm',
    data:{
        korisnickoIme: '',
		lozinka: '',
		objekti: {}
    },

	mounted() {
		axios
			.get("rest/sportFacilities")
			.then((response) => (this.objekti = response.data));
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
        }
    }
})