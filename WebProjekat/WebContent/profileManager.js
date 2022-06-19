var app = new Vue({
    el: '#profileManager',
    data:{
        manager: null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInManager')
		.then(response => {this.manager = response.data; this.username = response.data.username})
	},
    methods: {
		update: function(){
			axios.put('rest/users/updateManager/'+this.username, this.manager)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		}
    }
})