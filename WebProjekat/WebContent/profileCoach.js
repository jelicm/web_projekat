var app = new Vue({
    el: '#profileCoach',
    data:{
        coach: null,
        previous: null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInCoach')
		.then(response => {this.coach = response.data; this.previous = {...this.coach}; this.username = response.data.username})
	},
    methods: {
		update: function(event){
			event.preventDefault()
			if(!this.coach.name || !this.coach.surname || !this.coach.username || !this.coach.password
			 || !this.coach.gender || !this.coach.dateOfBirth){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
			axios.put('rest/users/updateCoach/' + this.username, this.coach)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		},
		restore: function(){
			this.coach = {...this.previous}
		}
    }
})