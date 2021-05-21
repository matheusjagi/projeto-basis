import { ItemModel } from "./item-model";

export class OfertaModel {
    id?: number;
    itemDtoId?: number;
    usuarioDtoId?: number;
    nomeUsuarioOfertante?: string;
    situacaoDtoId?: number;
    itensOfertados?: ItemModel[];
}
