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
package gplx.xowa.addons.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.dbs.*; import gplx.xowa.addons.apps.cfgs.mgrs.caches.*; import gplx.xowa.addons.apps.cfgs.mgrs.dflts.*; import gplx.xowa.addons.apps.cfgs.mgrs.types.*; import gplx.xowa.addons.apps.cfgs.mgrs.execs.*;
public class Xocfg_mgr implements Gfo_invk {
	public Xocfg_mgr() {
		this.dflt_mgr = new Xocfg_dflt_mgr(cache_mgr);
	}
	public Xocfg_cache_mgr Cache_mgr() {return cache_mgr;} private final    Xocfg_cache_mgr cache_mgr = new Xocfg_cache_mgr();
	public Xocfg_type_mgr Type_mgr() {return type_mgr;} private final    Xocfg_type_mgr type_mgr = new Xocfg_type_mgr();
	public Xocfg_dflt_mgr Dflt_mgr() {return dflt_mgr;} private final    Xocfg_dflt_mgr dflt_mgr;
	public Xocfg_exec_mgr Exec_mgr() {return exec_mgr;} private final    Xocfg_exec_mgr exec_mgr = new Xocfg_exec_mgr();
	public void Init_by_app(Xoa_app app) {
		cache_mgr.Init_by_app
		( gplx.xowa.addons.apps.cfgs.dbs.Xocfg_db_app.New_conn(app)
		, app.User().User_db_mgr().Conn());
		dflt_mgr.Init_by_app(app);
	}
	public void Sub_many_app	(Gfo_invk sub, String... keys)					{Bind_many(Bool_.N, sub, Xocfg_mgr.Ctx__app, keys);}
	public void Bind_many_app	(Gfo_invk sub, String... keys)					{Bind_many(Bool_.Y, sub, Xocfg_mgr.Ctx__app, keys);}
	public void Bind_many_wiki	(Gfo_invk sub, Xow_wiki wiki, String... keys)		{Bind_many(Bool_.Y, sub, wiki.Domain_itm().Abrv_xo_str(), keys);}
	private void Bind_many(boolean pub, Gfo_invk sub, String ctx, String... keys) {
		try {
			for (String key : keys) {
				cache_mgr.Sub(sub, ctx, key, key);
				if (pub) {
					String val = cache_mgr.Get(ctx, key);
					cache_mgr.Pub(ctx, key, val);
				}
			}
		}
		catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "bind failed: ctx=~{0} keys=~{1} err=~{2}", ctx, String_.AryXtoStr(keys), Err_.Message_gplx_log(e));
		}
	}
	public boolean Get_bool_app_or(String key, boolean or) {
		String rv = Get_str(Ctx__app, key);
		return rv == null ? or : Yn.parse_or(rv, or);
	}
	public boolean Get_bool_wiki_or(Xow_wiki wiki, String key, boolean or) {
		String rv = Get_str(wiki.Domain_itm().Abrv_xo_str(), key);
		return rv == null ? or : Yn.parse_or(rv, or);
	}
	public int Get_int_app_or(String key, int or) {
		String rv = cache_mgr.Get_or(Ctx__app, key, null);
		return rv == null ? or : Int_.parse_or(rv, or);
	}
	public long Get_long_app_or(String key, long or) {
		String rv = cache_mgr.Get_or(Ctx__app, key, null);
		return rv == null ? or : Long_.parse_or(rv, or);
	}
	public String[] Get_strary_app_or(String key, String dlm, String... or) {
		String rv = cache_mgr.Get_or(Ctx__app, key, null);
		return rv == null ? or : String_.Ary_parse(String_.Trim(rv), dlm);
	}
	public String Get_str_wiki_or(Xow_wiki wiki, String key, String or) {return cache_mgr.Get_or(wiki.Domain_itm().Abrv_xo_str(), key, or);}
	public String Get_str_app_or(String key, String or) {return cache_mgr.Get_or(Ctx__app, key, or);}
	public String Get_str_app(String key) {return Get_str(Xocfg_mgr.Ctx__app, key);}
	public boolean Get_bool_or(String ctx, String key, boolean or) {
		String rv = cache_mgr.Get(ctx, key);
		try		{return Yn.parse(rv);}
		catch	(Exception exc) {
			Err_.Noop(exc);
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse boolean; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public int Get_int_wiki_or(Xow_wiki wiki, String key, int or) {
		String rv = Get_str(wiki.Domain_itm().Abrv_xo_str(), key);
		try		{return Int_.parse(rv);}
		catch	(Exception exc) {
			Err_.Noop(exc);
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse int; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public int Get_int_or(String ctx, String key, int or) {
		String rv = cache_mgr.Get(ctx, key);
		try		{return Int_.parse(rv);}
		catch	(Exception exc) {
			Err_.Noop(exc);
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse int; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public Io_url Get_url_or(String ctx, String key, Io_url or) {
		String rv = cache_mgr.Get(ctx, key);
		try		{return Io_url_.new_any_(rv);}
		catch	(Exception exc) {
			Err_.Noop(exc);
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse int; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public String To_ctx(Xow_wiki wiki) {return wiki.Domain_itm().Abrv_xo_str();}
	public String Get_str(String ctx, String key) {return cache_mgr.Get(ctx, key);}
	public void Set_bool_app(String key, boolean val)		{Set_str(Xocfg_mgr.Ctx__app, key, Yn.To_str(val));}
	public void Set_float_app(String key, float val)	{Set_str(Xocfg_mgr.Ctx__app, key, Float_.To_str(val));}
	public void Set_str_app(String key, String val)		{Set_str(Xocfg_mgr.Ctx__app, key, val);}
	public void Set_int_app(String key, int val)		{Set_str(Xocfg_mgr.Ctx__app, key, Int_.To_str(val));}
	public void Set_bry_wiki(Xowe_wiki wiki, String key, byte[] val)	{Set_str(wiki.Domain_itm().Abrv_xo_str(), key, String_.new_u8(val));}
	public void Set_str(String ctx, String key, String val) {
		cache_mgr.Set(ctx, key, val);
	}
	public void Del(String ctx, String key) {
		cache_mgr.Del(ctx, key);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, "set"))					cache_mgr.Set			((String)m.ReadValAt(0), (String)m.ReadValAt(1), (String)m.ReadValAt(2));
		else if	(ctx.Match(k, "set_temp"))				cache_mgr.Set_wo_save	((String)m.ReadValAt(0), (String)m.ReadValAt(1), (String)m.ReadValAt(2));
		else if	(ctx.Match(k, "set_dflt"))				dflt_mgr.Add			((String)m.ReadValAt(0), (String)m.ReadValAt(1));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static String Ctx__app = "app";
	public static String[] Parse_io_cmd(String raw) {
		String[] rv = new String[2];
		rv[0] = "";
		rv[1] = "";
		int pos = String_.FindFwd(raw, "|");
		if (pos != Bry_find_.Not_found) {
			rv[0] = String_.Mid(raw, 0, pos);
			rv[1] = String_.Mid(raw, pos + 1);
		}
		return rv;
	}
}
