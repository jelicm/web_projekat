var app = new Vue({
    el: '#loginForm',
    data:{
        korisnickoIme: '',
		lozinka: '',
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