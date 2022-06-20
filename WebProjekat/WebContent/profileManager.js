var app = new Vue({
    el: '#profileManager',
    data:{
        manager: null,
        previous: null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInManager')
		.then(response => {this.manager = response.data; this.previous = {...this.manager}; this.username = response.data.username})
	},
    methods: {
		update: function(event){
			event.preventDefault()
			if(!this.manager.name || !this.manager.surname || !this.manager.username || !this.manager.password
			 || !this.manager.gender || !this.manager.dateOfBirth){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
			axios.put('rest/users/updateManager/' + this.username, this.manager)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		},
		restore: function(){
			this.manager = {...this.previous}
		}
    }
})