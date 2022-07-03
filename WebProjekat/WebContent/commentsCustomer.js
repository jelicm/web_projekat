var app = new Vue({
    el: '#commentsCustomer',
    data:{
		komentari: {}
    },

	mounted() {
		axios
			.get("rest/comments/approvedComments")
			.then((response) => {
				this.komentari = response.data;
			});
	}
})