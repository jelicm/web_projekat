var app = new Vue({
    el: '#commentApproval',
    data:{
		komentari: {}
    },

	mounted() {
		axios
			.get("rest/comments/waitingForApproval")
			.then((response) => {
				this.komentari = response.data;
			});
	},
	methods: {
        odobri: function(komentar){
				axios
				.post("rest/comments/approvedComment", komentar)
				.then(response => {
	                location.href=response.data 
	            })
		},
		odbij: function(komentar){
				axios
				.post("rest/comments/declinedComment", komentar)
				.then(response => {
	                location.href=response.data 
	            })
		}
	}
})