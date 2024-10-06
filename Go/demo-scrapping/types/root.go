package types

type AddReq struct {
	URL           string   `json:"url" binding: "required"`
	CardSelector  string   `json:"cardSelector" binding: "required"`
	InnerSelector string   `json:"innerSelector" binding: "required"`
	Tag           []string `json:"tag" binding: "required"`
}

type ViewReq struct {
	URL string `form:"url" biding:"required"`
}

type UpdateReq struct {
	URL           string   `json:"url" binding: "required"`
	CardSelector  string   `json:"cardSelector" binding: "required"`
	InnerSelector string   `json:"innerSelector" binding: "required"`
	Tag           []string `json:"tag" binding: "required"`
}

type DeleteReq struct {
	URL string `form:"url" biding:"required"`
}
