package network

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type admin struct {
	network *Network
}

func newAdmin(network *Network) {
	admin := &admin{network: network}

	basePath := "/admin"
	network.register(basePath+"/add", POST, admin.add)
	network.register(basePath+"/update", PUT, admin.update)
	network.register(basePath+"/view", GET, admin.view)
	network.register(basePath+"/view-all", GET, admin.viewAll)
	network.register(basePath+"/delete", DELETE, admin.delete)
}

func (admin *admin) add(context *gin.Context) {
	res(context, http.StatusCreated, "생성 입니다.", "테스트 입니다.")
}

func (admin *admin) update(context *gin.Context) {
	res(context, http.StatusOK, "수정 입니다.", "테스트 입니다.")
}

func (admin *admin) view(context *gin.Context) {
	res(context, http.StatusOK, "조회 입니디.", "테스트 입니다.")
}

func (admin *admin) viewAll(context *gin.Context) {
	res(context, http.StatusOK, "조회 입니다.", "테스트 입니다.")
}

func (admin *admin) delete(context *gin.Context) {
	res(context, http.StatusNoContent, "삭제 입니다.", "테스트 입니다.")
}
