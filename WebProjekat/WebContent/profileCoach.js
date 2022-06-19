var app = new Vue({
    el: '#profileCoach',
    data:{
        coach: null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInCoach')
		.then(response => {this.coach = response.data; this.username = response.data.username})
	},
    methods: {
		update: function(){
			axios.put('rest/users/updateCoach/'+this.username, this.coach)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		}
    }
})