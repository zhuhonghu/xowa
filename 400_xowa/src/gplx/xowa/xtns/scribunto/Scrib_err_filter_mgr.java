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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_err_filter_mgr implements GfoInvkAble {
	private final OrderedHash hash_by_mod = OrderedHash_.new_();
	public void Clear() {hash_by_mod.Clear();}
	public boolean Count_gt_0() {return hash_by_mod.Count() > 0;}
	public boolean Match(String mod, String fnc, String err) {
		ListAdp itms = Get_itms_or_null(mod, fnc); if (itms == null) return false;
		int itms_len = itms.Count();
		boolean match = false;
		for (int i = 0; i < itms_len; ++i) {
			Scrib_err_filter_itm itm = (Scrib_err_filter_itm)itms.FetchAt(i);
			if (String_.HasAtBgn(err, itm.Err())) {
				match = true;
				itm.Count_actl_add_1();
				break;
			}
		}
		return match;
	}
	public void Add(int count_expd, String mod, String fnc, String err, String comment) {
		ListAdp itms = Get_itms_or_null(mod, fnc);
		if (itms == null) itms = New_itms(mod, fnc);
		itms.Add(new Scrib_err_filter_itm(count_expd, mod, fnc, err, comment));
	}
	public String Print() {
		Bry_bfr bfr = Bry_bfr.new_(8);
		int i_len = hash_by_mod.Count();
		for (int i = 0; i < i_len; ++i) {
			OrderedHash fncs = (OrderedHash)hash_by_mod.FetchAt(i);
			int j_len = fncs.Count();
			for (int j = 0; j < j_len; ++j) {
				ListAdp errs = (ListAdp)fncs.FetchAt(j);
				int k_len = errs.Count();
				for (int k = 0; k < k_len; ++k) {
					Scrib_err_filter_itm err = (Scrib_err_filter_itm)errs.FetchAt(k);
					bfr.Add_int_variable(err.Count_actl()).Add_byte_pipe().Add_int_variable(err.Count_expd())
						.Add_byte_pipe().Add_str_utf8(err.Mod()).Add_byte_pipe().Add_str_utf8(err.Fnc()).Add_byte_pipe().Add_str_utf8(err.Err())
						.Add_byte_pipe().Add_str_utf8(err.Comment())
						.Add_byte_nl();
				}
			}
		}
		return bfr.Xto_str_and_clear();
	}
	private ListAdp Get_itms_or_null(String mod, String fnc) {
		OrderedHash hash_by_fnc = (OrderedHash)hash_by_mod.Fetch(mod); if (hash_by_fnc == null) return null;
		return (ListAdp)hash_by_fnc.Fetch(fnc);
	}
	private ListAdp New_itms(String mod, String fnc) {
		OrderedHash hash_by_fnc = (OrderedHash)hash_by_mod.Fetch(mod);
		if (hash_by_fnc == null) {
			hash_by_fnc = OrderedHash_.new_();
			hash_by_mod.Add(mod, hash_by_fnc);
		}
		ListAdp list_of_err = (ListAdp)hash_by_fnc.Fetch(fnc);
		if (list_of_err == null) {
			list_of_err = ListAdp_.new_();
			hash_by_fnc.Add(fnc, list_of_err);
		}
		return list_of_err;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add))	Add(m.ReadInt("count_expd"), m.ReadStr("mod"), m.ReadStr("fnc"), m.ReadStr("err"), m.ReadStr("comment"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_add = "add";
}
class Scrib_err_filter_itm {
	public Scrib_err_filter_itm(int count_expd, String mod, String fnc, String err, String comment) {this.count_expd = count_expd; this.mod = mod; this.err = err; this.fnc = fnc; this.comment = comment;}
	public String Mod() {return mod;} private final String mod;
	public String Fnc() {return fnc;} private final String fnc;
	public String Err() {return err;} private final String err;
	public String Comment() {return comment;} private final String comment;
	public int Count_expd() {return count_expd;} private final int count_expd;
	public int Count_actl() {return count_actl;} private int count_actl;
	public void Count_actl_add_1() {++count_actl;}
}
