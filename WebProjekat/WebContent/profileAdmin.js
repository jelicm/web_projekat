var app = new Vue({
    el: '#profileAdmin',
    data:{
        admin: null,
        previous : null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInAdmin')
		.then(response => {this.admin = response.data; this.previous = {...this.admin}; this.username = response.data.username})
	},
    methods: {
		update: function(event){
			event.preventDefault()
			if(!this.admin.name || !this.admin.surname || !this.admin.username || !this.admin.password
			 || !this.admin.gender || !this.admin.dateOfBirth){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
			axios.put('rest/users/updateAdmin/' + this.username, this.admin)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		},
		restore: function(){
			this.admin = {...this.previous}
		}
    }
})