import { ItemModel } from "./item-model";

export class OfertaModel {
    id?: number;
    itemDtoId?: number;
    usuarioDtoId?: number;
    situacaoDtoId?: number;
    itensOfertados?: ItemModel[];
}
