/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.apps.cfgs.specials.edits.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.langs.jsons.*;
import gplx.xowa.guis.cbks.*; import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.gui.*;
import gplx.xowa.addons.apps.cfgs.specials.edits.pages.*;
public class Xocfg_edit_service {
	private final    Xoa_app app;
	private final    Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(Xocfg_edit_special.Prototype.Special__meta().Ttl_bry());
	public Xocfg_edit_service(Xoa_app app) {
		this.app = app;
	}
	public void Upsert(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		String val = args.Get_as_str("val");
		app.Cfg().Set_str(ctx, key, val);
	}
	public void Revert(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		app.Cfg().Del(ctx, key);
	}
	public void Load(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		Xogui_mgr gui_mgr = new Xogui_mgr(new Xocfg_db_mgr(app.User().User_db_mgr().Conn()));
		Xogui_root gui_root = gui_mgr.Get_root(key, ctx, "en");
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.load__recv", gui_root.To_nde());
	}
}