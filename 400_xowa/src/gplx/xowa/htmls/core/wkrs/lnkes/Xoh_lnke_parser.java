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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*;
public class Xoh_lnke_parser {
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Html_tag_rdr tag_rdr, Html_tag anch) {// <a rel="nofollow" class="external autonumber_id" href="http://a.org">[1]</a>
		int rng_bgn = anch.Src_bgn(), rng_end = anch.Src_end();
		Html_atr href_atr = anch.Atrs__get_by(Html_atr_.Bry__href);
		int href_bgn = href_atr.Val_bgn(), href_end = href_atr.Val_end();
		byte lnke_type = anch.Atrs__cls_find_1st(Xoh_lnke_dict_.Hash);
		int autonumber_id = 0;
		switch (lnke_type) {
			case Xoh_lnke_dict_.Type__free:
				rng_end = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a).Src_end();		// find '</a>'; note that free is not recursive; EX: "https://a.org"
				break;
			case Xoh_lnke_dict_.Type__text:
				break;
			case Xoh_lnke_dict_.Type__auto:
				if (tag_rdr.Read_and_move(Byte_ascii.Brack_bgn)) {						// HTML tidy can reparent lnkes in strange ways; DATE:2015-08-25
					autonumber_id = tag_rdr.Read_int_to(Byte_ascii.Brack_end);			// extract int; EX: "<a ...>[123]</a>"
					rng_end = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a).Src_end();	// find '</a>'; note that auto is not recursive; EX: "[https://a.org]"
				}
				break;
		}
		hdoc_wkr.On_lnke(rng_bgn, rng_end, lnke_type, autonumber_id, href_bgn, href_end);
		return rng_end;
	}
}