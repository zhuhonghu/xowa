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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.files.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.xowa.xtns.pfuncs.ttls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.tmpls.*;
public class Xop_lnki_tkn extends Xop_tkn_itm_base {
	@Override public byte	Tkn_tid() {return tkn_tid;} private byte tkn_tid = Xop_tkn_itm_.Tid_lnki;
	public void				Tkn_tid_to_txt() {tkn_tid = Xop_tkn_itm_.Tid_txt;}
	public int				Ns_id() {return ns_id;} public Xop_lnki_tkn Ns_id_(int v) {ns_id = v; return this;} private int ns_id;
	public Xoa_ttl			Ttl() {return ttl;} public Xop_lnki_tkn Ttl_(Xoa_ttl v) {ttl = v; return this;} private Xoa_ttl ttl;
	public byte				Lnki_type() {return lnki_type;} private byte lnki_type = Xop_lnki_type.Id_null;
	public int				Tail_bgn() {return tail_bgn;} public Xop_lnki_tkn Tail_bgn_(int v) {tail_bgn = v; return this;} private int tail_bgn = -1;
	public int				Tail_end() {return tail_end;} public Xop_lnki_tkn Tail_end_(int v) {tail_end = v; return this;} private int tail_end = -1;
	public byte				Border() {return border;} public Xop_lnki_tkn Border_(byte v) {border = v; return this;} private byte border = Bool_.__byte;
	public byte				Align_h() {return align_h;} public Xop_lnki_tkn Align_h_(byte v) {if (align_h == Xop_lnki_align_h.Null) align_h = v; return this;} private byte align_h = Xop_lnki_align_h.Null;
	public byte				Align_v() {return align_v;} public Xop_lnki_tkn Align_v_(byte v) {align_v = v; return this;} private byte align_v = Byte_.Max_value_127;
	public int				W() {return w;} public Xop_lnki_tkn W_(int v) {w = v; return this;} private int w = Width_null;
	public int				H() {return h;} public Xop_lnki_tkn H_(int v) {h = v; return this;} private int h = Height_null;
	public byte[]			Lnki_cls() {return lnki_cls;} public void Lnki_cls_(byte[] v) {lnki_cls = v;} private byte[] lnki_cls;
	public boolean				Media_icon() {return media_icon;} public Xop_lnki_tkn Media_icon_n_() {media_icon = false; return this;} private boolean media_icon = true;
	public double			Upright() {return upright;} public Xop_lnki_tkn Upright_(double v) {upright = v; return this;} private double upright = Upright_null;
	public double	        Time() {return time;} public Xop_lnki_tkn Time_(double v) {time = v; return this;} private double time = Xof_lnki_time.Null;
	public int				Page() {return page;} public Xop_lnki_tkn Page_(int v) {page = v; return this;} private int page = Xof_lnki_page.Null;
	public Xop_tkn_itm		Trg_tkn() {return trg_tkn;} public Xop_lnki_tkn Trg_tkn_(Xop_tkn_itm v) {trg_tkn = v; return this;} private Xop_tkn_itm trg_tkn = Xop_tkn_null.Null_tkn;
	public Xop_tkn_itm		Caption_tkn() {return caption_tkn;} public Xop_lnki_tkn Caption_tkn_(Xop_tkn_itm v) {caption_tkn = v; return this;} private Xop_tkn_itm caption_tkn = Xop_tkn_null.Null_tkn;
	public boolean				Caption_tkn_pipe_trick() {return caption_tkn_pipe_trick;} public Xop_lnki_tkn Caption_tkn_pipe_trick_(boolean v) {caption_tkn_pipe_trick = v; return this;} private boolean caption_tkn_pipe_trick;
	public Xop_tkn_itm		Caption_val_tkn() {return caption_tkn == Xop_tkn_null.Null_tkn ? Arg_itm_tkn_null.Null_arg_itm : ((Arg_nde_tkn)caption_tkn).Val_tkn();}
	public Arg_nde_tkn		Link_tkn() {return link_tkn;} public Xop_lnki_tkn Link_tkn_(Arg_nde_tkn v) {link_tkn = v; return this;} Arg_nde_tkn link_tkn = Arg_nde_tkn.Null;
	public Arg_nde_tkn		Alt_tkn() {return alt_tkn;} public Xop_lnki_tkn Alt_tkn_(Arg_nde_tkn v) {alt_tkn = v; return this;} Arg_nde_tkn alt_tkn = Arg_nde_tkn.Null;
	public boolean				Alt_exists() {return alt_tkn != Arg_nde_tkn.Null;}
	public int				Subpage_tid() {return subpage_tid;} public Xop_lnki_tkn Subpage_tid_(int v) {subpage_tid = v; return this;} private int subpage_tid = Pfunc_rel2abs.Id_null;
	public boolean				Subpage_slash_at_end() {return subpage_slash_at_end;} public Xop_lnki_tkn Subpage_slash_at_end_(boolean v) {subpage_slash_at_end = v; return this;} private boolean subpage_slash_at_end;
	public int				Html_uid() {return html_uid;} public Xop_lnki_tkn Html_uid_(int v) {html_uid = v; return this;} private int html_uid;
	public int				Pipe_count() {return pipe_count;} private int pipe_count;
	public boolean				Pipe_count_is_zero() {return pipe_count++ == 0;} 
	public boolean				Xtn_sites_link() {return xtn_sites_link;} public void Xtn_sites_link_(boolean v) {xtn_sites_link = v;} private boolean xtn_sites_link;
	public Xoh_file_img_wkr Lnki_file_wkr() {return lnki_file_wkr;} public void Lnki_file_wkr_(Xoh_file_img_wkr v) {lnki_file_wkr = v;} private Xoh_file_img_wkr lnki_file_wkr;
	public byte[] Ttl_ary() {
		return ttl.ForceLiteralLink() || ns_id != Xow_ns_.Tid__main		// if [[:]] or non-main (Category, Template)
			? ttl.Full_txt()											// use full_txt (no initial colon; capitalize first)
			: ttl.Raw();												// use raw (preserve case, white-spaces)
	}
	public boolean Caption_exists() {
		return !((caption_tkn == Xop_tkn_null.Null_tkn)		// trg only; no caption: EX: [[a]] vs. [[a|b]] which has a trg of a and a caption of b
				||	(ns_id == Xow_ns_.Tid__category			// a Category only has a target; any caption is ignored; EX: [[Category:a|b], b is ignored			
					&& !ttl.ForceLiteralLink()));				
	}
	public Xop_lnki_tkn Lnki_type_(byte v) {
		if (lnki_type == Xop_lnki_type.Id_null)	// NOTE:per MW:1.25.2, only use 1st argument of thumb|frame|frameless;/includes/parser/Parser.php; // use first appearing option, discard others.; DATE:2015-11-01
			lnki_type = v;
		return this;
	}
	public static final double Upright_null = -1;
	public static final int Width_null = -1, Height_null = -1;
}
