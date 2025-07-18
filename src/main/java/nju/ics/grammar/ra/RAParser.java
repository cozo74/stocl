// Generated from RA.g4 by ANTLR 4.13.2

    package nju.ics.grammar.ra;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class RAParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, ID=38, INT_LITERAL=39, 
		REAL_LITERAL=40, STRING_LITERAL=41, NULL_LITERAL=42, NEWLINE=43, LINE_COMMENT=44, 
		WS=45;
	public static final int
		RULE_raExpr = 0, RULE_queryExpr = 1, RULE_query = 2, RULE_relationOp = 3, 
		RULE_boolExpr = 4, RULE_equalityExpr = 5, RULE_compOp = 6, RULE_stringValue = 7, 
		RULE_arithmeticExpr = 8, RULE_arithmeticValue = 9, RULE_aggFunc = 10, 
		RULE_fieldList = 11, RULE_renameList = 12, RULE_renamePair = 13, RULE_field = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"raExpr", "queryExpr", "query", "relationOp", "boolExpr", "equalityExpr", 
			"compOp", "stringValue", "arithmeticExpr", "arithmeticValue", "aggFunc", 
			"fieldList", "renameList", "renamePair", "field"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Database'", "':'", "'qry'", "';'", "'Join'", "'Selection'", "'{'", 
			"'}'", "'Projection'", "','", "'Rename'", "'Group'", "'('", "')'", "'Product'", 
			"'Difference'", "'Union'", "'Intersection'", "'not'", "'and'", "'or'", 
			"'='", "'<>'", "'<'", "'>'", "'>='", "'<='", "'-'", "'*'", "'/'", "'+'", 
			"'min'", "'max'", "'sum'", "'avg'", "'count'", "'->'", null, null, null, 
			null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "ID", "INT_LITERAL", "REAL_LITERAL", "STRING_LITERAL", "NULL_LITERAL", 
			"NEWLINE", "LINE_COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RA.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RAParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RaExprContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(RAParser.ID, 0); }
		public TerminalNode EOF() { return getToken(RAParser.EOF, 0); }
		public List<QueryExprContext> queryExpr() {
			return getRuleContexts(QueryExprContext.class);
		}
		public QueryExprContext queryExpr(int i) {
			return getRuleContext(QueryExprContext.class,i);
		}
		public RaExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_raExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitRaExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RaExprContext raExpr() throws RecognitionException {
		RaExprContext _localctx = new RaExprContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_raExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(T__0);
			setState(31);
			match(ID);
			setState(32);
			match(T__1);
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(33);
				queryExpr();
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryExprContext extends ParserRuleContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode ID() { return getToken(RAParser.ID, 0); }
		public QueryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitQueryExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryExprContext queryExpr() throws RecognitionException {
		QueryExprContext _localctx = new QueryExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_queryExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(T__2);
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(42);
				match(ID);
				}
			}

			setState(45);
			match(T__1);
			setState(46);
			query(0);
			setState(47);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryContext extends ParserRuleContext {
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
	 
		public QueryContext() { }
		public void copyFrom(QueryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinWithConditionContext extends QueryContext {
		public List<QueryContext> query() {
			return getRuleContexts(QueryContext.class);
		}
		public QueryContext query(int i) {
			return getRuleContext(QueryContext.class,i);
		}
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public JoinWithConditionContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitJoinWithCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GroupContext extends QueryContext {
		public AggFuncContext aggFunc() {
			return getRuleContext(AggFuncContext.class,0);
		}
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public FieldListContext fieldList() {
			return getRuleContext(FieldListContext.class,0);
		}
		public GroupContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitGroup(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QueryParenContext extends QueryContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public QueryParenContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitQueryParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelectionContext extends QueryContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public SelectionContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitSelection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RelationIDContext extends QueryContext {
		public TerminalNode ID() { return getToken(RAParser.ID, 0); }
		public RelationIDContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitRelationID(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ProjectionContext extends QueryContext {
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public ProjectionContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitProjection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinContext extends QueryContext {
		public List<QueryContext> query() {
			return getRuleContexts(QueryContext.class);
		}
		public QueryContext query(int i) {
			return getRuleContext(QueryContext.class,i);
		}
		public JoinContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QueryRelationOpContext extends QueryContext {
		public List<QueryContext> query() {
			return getRuleContexts(QueryContext.class);
		}
		public QueryContext query(int i) {
			return getRuleContext(QueryContext.class,i);
		}
		public RelationOpContext relationOp() {
			return getRuleContext(RelationOpContext.class,0);
		}
		public QueryRelationOpContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitQueryRelationOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RenameContext extends QueryContext {
		public RenameListContext renameList() {
			return getRuleContext(RenameListContext.class,0);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public RenameContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitRename(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		return query(0);
	}

	private QueryContext query(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		QueryContext _localctx = new QueryContext(_ctx, _parentState);
		QueryContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_query, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				_localctx = new RelationIDContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(50);
				match(ID);
				}
				break;
			case T__5:
				{
				_localctx = new SelectionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(51);
				match(T__5);
				setState(52);
				match(T__6);
				setState(53);
				boolExpr(0);
				setState(54);
				match(T__7);
				setState(55);
				match(T__6);
				setState(56);
				query(0);
				setState(57);
				match(T__7);
				}
				break;
			case T__8:
				{
				_localctx = new ProjectionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(59);
				match(T__8);
				setState(60);
				match(T__6);
				setState(61);
				arithmeticExpr(0);
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(62);
					match(T__9);
					setState(63);
					arithmeticExpr(0);
					}
					}
					setState(68);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(69);
				match(T__7);
				setState(70);
				match(T__6);
				setState(71);
				query(0);
				setState(72);
				match(T__7);
				}
				break;
			case T__10:
				{
				_localctx = new RenameContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(74);
				match(T__10);
				setState(75);
				match(T__6);
				setState(76);
				renameList();
				setState(77);
				match(T__7);
				setState(78);
				match(T__6);
				setState(79);
				query(0);
				setState(80);
				match(T__7);
				}
				break;
			case T__11:
				{
				_localctx = new GroupContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(82);
				match(T__11);
				setState(83);
				match(T__6);
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(84);
					fieldList();
					setState(85);
					match(T__9);
					}
				}

				setState(89);
				aggFunc();
				setState(90);
				match(T__12);
				setState(91);
				field();
				setState(92);
				match(T__13);
				setState(93);
				match(T__7);
				setState(94);
				match(T__6);
				setState(95);
				query(0);
				setState(96);
				match(T__7);
				}
				break;
			case T__12:
				{
				_localctx = new QueryParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(98);
				match(T__12);
				setState(99);
				query(0);
				setState(100);
				match(T__13);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(118);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(116);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new JoinContext(new QueryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_query);
						setState(104);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(105);
						match(T__4);
						setState(106);
						query(9);
						}
						break;
					case 2:
						{
						_localctx = new JoinWithConditionContext(new QueryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_query);
						setState(107);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(108);
						match(T__4);
						setState(109);
						boolExpr(0);
						setState(110);
						query(8);
						}
						break;
					case 3:
						{
						_localctx = new QueryRelationOpContext(new QueryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_query);
						setState(112);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(113);
						relationOp();
						setState(114);
						query(7);
						}
						break;
					}
					} 
				}
				setState(120);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationOpContext extends ParserRuleContext {
		public RelationOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationOp; }
	 
		public RelationOpContext() { }
		public void copyFrom(RelationOpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntersectionContext extends RelationOpContext {
		public IntersectionContext(RelationOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitIntersection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ProductContext extends RelationOpContext {
		public ProductContext(RelationOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitProduct(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DifferenceContext extends RelationOpContext {
		public DifferenceContext(RelationOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnionContext extends RelationOpContext {
		public UnionContext(RelationOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitUnion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationOpContext relationOp() throws RecognitionException {
		RelationOpContext _localctx = new RelationOpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_relationOp);
		try {
			setState(125);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
				_localctx = new ProductContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				match(T__14);
				}
				break;
			case T__15:
				_localctx = new DifferenceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				match(T__15);
				}
				break;
			case T__16:
				_localctx = new UnionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(123);
				match(T__16);
				}
				break;
			case T__17:
				_localctx = new IntersectionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(124);
				match(T__17);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BoolExprContext extends ParserRuleContext {
		public BoolExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolExpr; }
	 
		public BoolExprContext() { }
		public void copyFrom(BoolExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolNotContext extends BoolExprContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public BoolNotContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitBoolNot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolEqualityContext extends BoolExprContext {
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public BoolEqualityContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitBoolEquality(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolParenContext extends BoolExprContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public BoolParenContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitBoolParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolOrContext extends BoolExprContext {
		public List<BoolExprContext> boolExpr() {
			return getRuleContexts(BoolExprContext.class);
		}
		public BoolExprContext boolExpr(int i) {
			return getRuleContext(BoolExprContext.class,i);
		}
		public BoolOrContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitBoolOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolAndContext extends BoolExprContext {
		public List<BoolExprContext> boolExpr() {
			return getRuleContexts(BoolExprContext.class);
		}
		public BoolExprContext boolExpr(int i) {
			return getRuleContext(BoolExprContext.class,i);
		}
		public BoolAndContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitBoolAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolExprContext boolExpr() throws RecognitionException {
		return boolExpr(0);
	}

	private BoolExprContext boolExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BoolExprContext _localctx = new BoolExprContext(_ctx, _parentState);
		BoolExprContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_boolExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new BoolNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(128);
				match(T__18);
				setState(129);
				boolExpr(5);
				}
				break;
			case 2:
				{
				_localctx = new BoolEqualityContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(130);
				equalityExpr();
				}
				break;
			case 3:
				{
				_localctx = new BoolParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(131);
				match(T__12);
				setState(132);
				boolExpr(0);
				setState(133);
				match(T__13);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(145);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(143);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new BoolAndContext(new BoolExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
						setState(137);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(138);
						match(T__19);
						setState(139);
						boolExpr(5);
						}
						break;
					case 2:
						{
						_localctx = new BoolOrContext(new BoolExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
						setState(140);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(141);
						match(T__20);
						setState(142);
						boolExpr(4);
						}
						break;
					}
					} 
				}
				setState(147);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprContext extends ParserRuleContext {
		public EqualityExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpr; }
	 
		public EqualityExprContext() { }
		public void copyFrom(EqualityExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityParenContext extends EqualityExprContext {
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public EqualityParenContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitEqualityParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityStringEqualContext extends EqualityExprContext {
		public List<StringValueContext> stringValue() {
			return getRuleContexts(StringValueContext.class);
		}
		public StringValueContext stringValue(int i) {
			return getRuleContext(StringValueContext.class,i);
		}
		public EqualityStringEqualContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitEqualityStringEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityArithCompArithContext extends EqualityExprContext {
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public EqualityArithCompArithContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitEqualityArithCompArith(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityStringNotEqualContext extends EqualityExprContext {
		public List<StringValueContext> stringValue() {
			return getRuleContexts(StringValueContext.class);
		}
		public StringValueContext stringValue(int i) {
			return getRuleContext(StringValueContext.class,i);
		}
		public EqualityStringNotEqualContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitEqualityStringNotEqual(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_equalityExpr);
		try {
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new EqualityArithCompArithContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				arithmeticExpr(0);
				setState(149);
				compOp();
				setState(150);
				arithmeticExpr(0);
				}
				break;
			case 2:
				_localctx = new EqualityParenContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(152);
				match(T__12);
				setState(153);
				equalityExpr();
				setState(154);
				match(T__13);
				}
				break;
			case 3:
				_localctx = new EqualityStringEqualContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(156);
				stringValue();
				setState(157);
				match(T__21);
				setState(158);
				stringValue();
				}
				break;
			case 4:
				_localctx = new EqualityStringNotEqualContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(160);
				stringValue();
				setState(161);
				match(T__22);
				setState(162);
				stringValue();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompOpContext extends ParserRuleContext {
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitCompOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 264241152L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringValueContext extends ParserRuleContext {
		public StringValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringValue; }
	 
		public StringValueContext() { }
		public void copyFrom(StringValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends StringValueContext {
		public TerminalNode STRING_LITERAL() { return getToken(RAParser.STRING_LITERAL, 0); }
		public StringLiteralContext(StringValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringFieldContext extends StringValueContext {
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public StringFieldContext(StringValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitStringField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringValueContext stringValue() throws RecognitionException {
		StringValueContext _localctx = new StringValueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_stringValue);
		try {
			setState(170);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LITERAL:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				match(STRING_LITERAL);
				}
				break;
			case ID:
				_localctx = new StringFieldContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				field();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticExprContext extends ParserRuleContext {
		public ArithmeticExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticExpr; }
	 
		public ArithmeticExprContext() { }
		public void copyFrom(ArithmeticExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticAddSubContext extends ArithmeticExprContext {
		public Token op;
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public ArithmeticAddSubContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticAddSub(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticNegateContext extends ArithmeticExprContext {
		public ArithmeticExprContext arithmeticExpr() {
			return getRuleContext(ArithmeticExprContext.class,0);
		}
		public ArithmeticNegateContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticNegate(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticExprValueContext extends ArithmeticExprContext {
		public ArithmeticValueContext arithmeticValue() {
			return getRuleContext(ArithmeticValueContext.class,0);
		}
		public ArithmeticExprValueContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticExprValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticParenContext extends ArithmeticExprContext {
		public ArithmeticExprContext arithmeticExpr() {
			return getRuleContext(ArithmeticExprContext.class,0);
		}
		public ArithmeticParenContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticMulDivContext extends ArithmeticExprContext {
		public Token op;
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public ArithmeticMulDivContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticMulDiv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticExprContext arithmeticExpr() throws RecognitionException {
		return arithmeticExpr(0);
	}

	private ArithmeticExprContext arithmeticExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArithmeticExprContext _localctx = new ArithmeticExprContext(_ctx, _parentState);
		ArithmeticExprContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_arithmeticExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
				{
				_localctx = new ArithmeticNegateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(173);
				match(T__27);
				setState(174);
				arithmeticExpr(5);
				}
				break;
			case T__12:
				{
				_localctx = new ArithmeticParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				match(T__12);
				setState(176);
				arithmeticExpr(0);
				setState(177);
				match(T__13);
				}
				break;
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case ID:
			case INT_LITERAL:
			case REAL_LITERAL:
				{
				_localctx = new ArithmeticExprValueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(179);
				arithmeticValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(190);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(188);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticMulDivContext(new ArithmeticExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpr);
						setState(182);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(183);
						((ArithmeticMulDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__28 || _la==T__29) ) {
							((ArithmeticMulDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(184);
						arithmeticExpr(5);
						}
						break;
					case 2:
						{
						_localctx = new ArithmeticAddSubContext(new ArithmeticExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpr);
						setState(185);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(186);
						((ArithmeticAddSubContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__27 || _la==T__30) ) {
							((ArithmeticAddSubContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(187);
						arithmeticExpr(4);
						}
						break;
					}
					} 
				}
				setState(192);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticValueContext extends ParserRuleContext {
		public ArithmeticValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticValue; }
	 
		public ArithmeticValueContext() { }
		public void copyFrom(ArithmeticValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticIntLiteralContext extends ArithmeticValueContext {
		public TerminalNode INT_LITERAL() { return getToken(RAParser.INT_LITERAL, 0); }
		public ArithmeticIntLiteralContext(ArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticAggFuncFieldContext extends ArithmeticValueContext {
		public AggFuncContext aggFunc() {
			return getRuleContext(AggFuncContext.class,0);
		}
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public ArithmeticAggFuncFieldContext(ArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticAggFuncField(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticRealLiteralContext extends ArithmeticValueContext {
		public TerminalNode REAL_LITERAL() { return getToken(RAParser.REAL_LITERAL, 0); }
		public ArithmeticRealLiteralContext(ArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticRealLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticFieldContext extends ArithmeticValueContext {
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public ArithmeticFieldContext(ArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitArithmeticField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticValueContext arithmeticValue() throws RecognitionException {
		ArithmeticValueContext _localctx = new ArithmeticValueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_arithmeticValue);
		try {
			setState(201);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_LITERAL:
				_localctx = new ArithmeticIntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
				match(INT_LITERAL);
				}
				break;
			case REAL_LITERAL:
				_localctx = new ArithmeticRealLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(194);
				match(REAL_LITERAL);
				}
				break;
			case ID:
				_localctx = new ArithmeticFieldContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(195);
				field();
				}
				break;
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
				_localctx = new ArithmeticAggFuncFieldContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(196);
				aggFunc();
				setState(197);
				match(T__12);
				setState(198);
				field();
				setState(199);
				match(T__13);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AggFuncContext extends ParserRuleContext {
		public AggFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggFunc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitAggFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AggFuncContext aggFunc() throws RecognitionException {
		AggFuncContext _localctx = new AggFuncContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_aggFunc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 133143986176L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldListContext extends ParserRuleContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public FieldListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitFieldList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldListContext fieldList() throws RecognitionException {
		FieldListContext _localctx = new FieldListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_fieldList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			field();
			setState(210);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(206);
					match(T__9);
					setState(207);
					field();
					}
					} 
				}
				setState(212);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RenameListContext extends ParserRuleContext {
		public List<RenamePairContext> renamePair() {
			return getRuleContexts(RenamePairContext.class);
		}
		public RenamePairContext renamePair(int i) {
			return getRuleContext(RenamePairContext.class,i);
		}
		public RenameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renameList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitRenameList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RenameListContext renameList() throws RecognitionException {
		RenameListContext _localctx = new RenameListContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_renameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			renamePair();
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(214);
				match(T__9);
				setState(215);
				renamePair();
				}
				}
				setState(220);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RenamePairContext extends ParserRuleContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public RenamePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renamePair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitRenamePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RenamePairContext renamePair() throws RecognitionException {
		RenamePairContext _localctx = new RenamePairContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_renamePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			field();
			setState(222);
			match(T__36);
			setState(223);
			field();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(RAParser.ID, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RAVisitor ) return ((RAVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return query_sempred((QueryContext)_localctx, predIndex);
		case 4:
			return boolExpr_sempred((BoolExprContext)_localctx, predIndex);
		case 8:
			return arithmeticExpr_sempred((ArithmeticExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean query_sempred(QueryContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		}
		return true;
	}
	private boolean boolExpr_sempred(BoolExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean arithmeticExpr_sempred(ArithmeticExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001-\u00e4\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000#\b\u0000\n\u0000\f\u0000&\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001,\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0005\u0002A\b\u0002\n\u0002\f\u0002D\t\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"X\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u0002g\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002u\b\u0002"+
		"\n\u0002\f\u0002x\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003~\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u0088\b\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0005\u0004\u0090\b\u0004\n\u0004\f\u0004\u0093\t\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u00a5\b\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0003\u0007\u00ab\b\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00b5\b\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u00bd\b\b\n\b\f\b\u00c0"+
		"\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003"+
		"\t\u00ca\b\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0005"+
		"\u000b\u00d1\b\u000b\n\u000b\f\u000b\u00d4\t\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0005\f\u00d9\b\f\n\f\f\f\u00dc\t\f\u0001\r\u0001\r\u0001\r\u0001\r"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0000\u0003\u0004\b\u0010\u000f\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u0000\u0004\u0001\u0000\u0016\u001b\u0001\u0000\u001d\u001e\u0002\u0000"+
		"\u001c\u001c\u001f\u001f\u0001\u0000 $\u00f4\u0000\u001e\u0001\u0000\u0000"+
		"\u0000\u0002)\u0001\u0000\u0000\u0000\u0004f\u0001\u0000\u0000\u0000\u0006"+
		"}\u0001\u0000\u0000\u0000\b\u0087\u0001\u0000\u0000\u0000\n\u00a4\u0001"+
		"\u0000\u0000\u0000\f\u00a6\u0001\u0000\u0000\u0000\u000e\u00aa\u0001\u0000"+
		"\u0000\u0000\u0010\u00b4\u0001\u0000\u0000\u0000\u0012\u00c9\u0001\u0000"+
		"\u0000\u0000\u0014\u00cb\u0001\u0000\u0000\u0000\u0016\u00cd\u0001\u0000"+
		"\u0000\u0000\u0018\u00d5\u0001\u0000\u0000\u0000\u001a\u00dd\u0001\u0000"+
		"\u0000\u0000\u001c\u00e1\u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0001"+
		"\u0000\u0000\u001f \u0005&\u0000\u0000 $\u0005\u0002\u0000\u0000!#\u0003"+
		"\u0002\u0001\u0000\"!\u0001\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000"+
		"$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%\'\u0001\u0000\u0000"+
		"\u0000&$\u0001\u0000\u0000\u0000\'(\u0005\u0000\u0000\u0001(\u0001\u0001"+
		"\u0000\u0000\u0000)+\u0005\u0003\u0000\u0000*,\u0005&\u0000\u0000+*\u0001"+
		"\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,-\u0001\u0000\u0000\u0000"+
		"-.\u0005\u0002\u0000\u0000./\u0003\u0004\u0002\u0000/0\u0005\u0004\u0000"+
		"\u00000\u0003\u0001\u0000\u0000\u000012\u0006\u0002\uffff\uffff\u0000"+
		"2g\u0005&\u0000\u000034\u0005\u0006\u0000\u000045\u0005\u0007\u0000\u0000"+
		"56\u0003\b\u0004\u000067\u0005\b\u0000\u000078\u0005\u0007\u0000\u0000"+
		"89\u0003\u0004\u0002\u00009:\u0005\b\u0000\u0000:g\u0001\u0000\u0000\u0000"+
		";<\u0005\t\u0000\u0000<=\u0005\u0007\u0000\u0000=B\u0003\u0010\b\u0000"+
		">?\u0005\n\u0000\u0000?A\u0003\u0010\b\u0000@>\u0001\u0000\u0000\u0000"+
		"AD\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000"+
		"\u0000CE\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000EF\u0005\b\u0000"+
		"\u0000FG\u0005\u0007\u0000\u0000GH\u0003\u0004\u0002\u0000HI\u0005\b\u0000"+
		"\u0000Ig\u0001\u0000\u0000\u0000JK\u0005\u000b\u0000\u0000KL\u0005\u0007"+
		"\u0000\u0000LM\u0003\u0018\f\u0000MN\u0005\b\u0000\u0000NO\u0005\u0007"+
		"\u0000\u0000OP\u0003\u0004\u0002\u0000PQ\u0005\b\u0000\u0000Qg\u0001\u0000"+
		"\u0000\u0000RS\u0005\f\u0000\u0000SW\u0005\u0007\u0000\u0000TU\u0003\u0016"+
		"\u000b\u0000UV\u0005\n\u0000\u0000VX\u0001\u0000\u0000\u0000WT\u0001\u0000"+
		"\u0000\u0000WX\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000YZ\u0003"+
		"\u0014\n\u0000Z[\u0005\r\u0000\u0000[\\\u0003\u001c\u000e\u0000\\]\u0005"+
		"\u000e\u0000\u0000]^\u0005\b\u0000\u0000^_\u0005\u0007\u0000\u0000_`\u0003"+
		"\u0004\u0002\u0000`a\u0005\b\u0000\u0000ag\u0001\u0000\u0000\u0000bc\u0005"+
		"\r\u0000\u0000cd\u0003\u0004\u0002\u0000de\u0005\u000e\u0000\u0000eg\u0001"+
		"\u0000\u0000\u0000f1\u0001\u0000\u0000\u0000f3\u0001\u0000\u0000\u0000"+
		"f;\u0001\u0000\u0000\u0000fJ\u0001\u0000\u0000\u0000fR\u0001\u0000\u0000"+
		"\u0000fb\u0001\u0000\u0000\u0000gv\u0001\u0000\u0000\u0000hi\n\b\u0000"+
		"\u0000ij\u0005\u0005\u0000\u0000ju\u0003\u0004\u0002\tkl\n\u0007\u0000"+
		"\u0000lm\u0005\u0005\u0000\u0000mn\u0003\b\u0004\u0000no\u0003\u0004\u0002"+
		"\bou\u0001\u0000\u0000\u0000pq\n\u0006\u0000\u0000qr\u0003\u0006\u0003"+
		"\u0000rs\u0003\u0004\u0002\u0007su\u0001\u0000\u0000\u0000th\u0001\u0000"+
		"\u0000\u0000tk\u0001\u0000\u0000\u0000tp\u0001\u0000\u0000\u0000ux\u0001"+
		"\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000"+
		"w\u0005\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000y~\u0005\u000f"+
		"\u0000\u0000z~\u0005\u0010\u0000\u0000{~\u0005\u0011\u0000\u0000|~\u0005"+
		"\u0012\u0000\u0000}y\u0001\u0000\u0000\u0000}z\u0001\u0000\u0000\u0000"+
		"}{\u0001\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000~\u0007\u0001\u0000"+
		"\u0000\u0000\u007f\u0080\u0006\u0004\uffff\uffff\u0000\u0080\u0081\u0005"+
		"\u0013\u0000\u0000\u0081\u0088\u0003\b\u0004\u0005\u0082\u0088\u0003\n"+
		"\u0005\u0000\u0083\u0084\u0005\r\u0000\u0000\u0084\u0085\u0003\b\u0004"+
		"\u0000\u0085\u0086\u0005\u000e\u0000\u0000\u0086\u0088\u0001\u0000\u0000"+
		"\u0000\u0087\u007f\u0001\u0000\u0000\u0000\u0087\u0082\u0001\u0000\u0000"+
		"\u0000\u0087\u0083\u0001\u0000\u0000\u0000\u0088\u0091\u0001\u0000\u0000"+
		"\u0000\u0089\u008a\n\u0004\u0000\u0000\u008a\u008b\u0005\u0014\u0000\u0000"+
		"\u008b\u0090\u0003\b\u0004\u0005\u008c\u008d\n\u0003\u0000\u0000\u008d"+
		"\u008e\u0005\u0015\u0000\u0000\u008e\u0090\u0003\b\u0004\u0004\u008f\u0089"+
		"\u0001\u0000\u0000\u0000\u008f\u008c\u0001\u0000\u0000\u0000\u0090\u0093"+
		"\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0091\u0092"+
		"\u0001\u0000\u0000\u0000\u0092\t\u0001\u0000\u0000\u0000\u0093\u0091\u0001"+
		"\u0000\u0000\u0000\u0094\u0095\u0003\u0010\b\u0000\u0095\u0096\u0003\f"+
		"\u0006\u0000\u0096\u0097\u0003\u0010\b\u0000\u0097\u00a5\u0001\u0000\u0000"+
		"\u0000\u0098\u0099\u0005\r\u0000\u0000\u0099\u009a\u0003\n\u0005\u0000"+
		"\u009a\u009b\u0005\u000e\u0000\u0000\u009b\u00a5\u0001\u0000\u0000\u0000"+
		"\u009c\u009d\u0003\u000e\u0007\u0000\u009d\u009e\u0005\u0016\u0000\u0000"+
		"\u009e\u009f\u0003\u000e\u0007\u0000\u009f\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a0\u00a1\u0003\u000e\u0007\u0000\u00a1\u00a2\u0005\u0017\u0000\u0000"+
		"\u00a2\u00a3\u0003\u000e\u0007\u0000\u00a3\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a4\u0094\u0001\u0000\u0000\u0000\u00a4\u0098\u0001\u0000\u0000\u0000"+
		"\u00a4\u009c\u0001\u0000\u0000\u0000\u00a4\u00a0\u0001\u0000\u0000\u0000"+
		"\u00a5\u000b\u0001\u0000\u0000\u0000\u00a6\u00a7\u0007\u0000\u0000\u0000"+
		"\u00a7\r\u0001\u0000\u0000\u0000\u00a8\u00ab\u0005)\u0000\u0000\u00a9"+
		"\u00ab\u0003\u001c\u000e\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00aa"+
		"\u00a9\u0001\u0000\u0000\u0000\u00ab\u000f\u0001\u0000\u0000\u0000\u00ac"+
		"\u00ad\u0006\b\uffff\uffff\u0000\u00ad\u00ae\u0005\u001c\u0000\u0000\u00ae"+
		"\u00b5\u0003\u0010\b\u0005\u00af\u00b0\u0005\r\u0000\u0000\u00b0\u00b1"+
		"\u0003\u0010\b\u0000\u00b1\u00b2\u0005\u000e\u0000\u0000\u00b2\u00b5\u0001"+
		"\u0000\u0000\u0000\u00b3\u00b5\u0003\u0012\t\u0000\u00b4\u00ac\u0001\u0000"+
		"\u0000\u0000\u00b4\u00af\u0001\u0000\u0000\u0000\u00b4\u00b3\u0001\u0000"+
		"\u0000\u0000\u00b5\u00be\u0001\u0000\u0000\u0000\u00b6\u00b7\n\u0004\u0000"+
		"\u0000\u00b7\u00b8\u0007\u0001\u0000\u0000\u00b8\u00bd\u0003\u0010\b\u0005"+
		"\u00b9\u00ba\n\u0003\u0000\u0000\u00ba\u00bb\u0007\u0002\u0000\u0000\u00bb"+
		"\u00bd\u0003\u0010\b\u0004\u00bc\u00b6\u0001\u0000\u0000\u0000\u00bc\u00b9"+
		"\u0001\u0000\u0000\u0000\u00bd\u00c0\u0001\u0000\u0000\u0000\u00be\u00bc"+
		"\u0001\u0000\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf\u0011"+
		"\u0001\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c1\u00ca"+
		"\u0005\'\u0000\u0000\u00c2\u00ca\u0005(\u0000\u0000\u00c3\u00ca\u0003"+
		"\u001c\u000e\u0000\u00c4\u00c5\u0003\u0014\n\u0000\u00c5\u00c6\u0005\r"+
		"\u0000\u0000\u00c6\u00c7\u0003\u001c\u000e\u0000\u00c7\u00c8\u0005\u000e"+
		"\u0000\u0000\u00c8\u00ca\u0001\u0000\u0000\u0000\u00c9\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c9\u00c2\u0001\u0000\u0000\u0000\u00c9\u00c3\u0001\u0000"+
		"\u0000\u0000\u00c9\u00c4\u0001\u0000\u0000\u0000\u00ca\u0013\u0001\u0000"+
		"\u0000\u0000\u00cb\u00cc\u0007\u0003\u0000\u0000\u00cc\u0015\u0001\u0000"+
		"\u0000\u0000\u00cd\u00d2\u0003\u001c\u000e\u0000\u00ce\u00cf\u0005\n\u0000"+
		"\u0000\u00cf\u00d1\u0003\u001c\u000e\u0000\u00d0\u00ce\u0001\u0000\u0000"+
		"\u0000\u00d1\u00d4\u0001\u0000\u0000\u0000\u00d2\u00d0\u0001\u0000\u0000"+
		"\u0000\u00d2\u00d3\u0001\u0000\u0000\u0000\u00d3\u0017\u0001\u0000\u0000"+
		"\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000\u00d5\u00da\u0003\u001a\r\u0000"+
		"\u00d6\u00d7\u0005\n\u0000\u0000\u00d7\u00d9\u0003\u001a\r\u0000\u00d8"+
		"\u00d6\u0001\u0000\u0000\u0000\u00d9\u00dc\u0001\u0000\u0000\u0000\u00da"+
		"\u00d8\u0001\u0000\u0000\u0000\u00da\u00db\u0001\u0000\u0000\u0000\u00db"+
		"\u0019\u0001\u0000\u0000\u0000\u00dc\u00da\u0001\u0000\u0000\u0000\u00dd"+
		"\u00de\u0003\u001c\u000e\u0000\u00de\u00df\u0005%\u0000\u0000\u00df\u00e0"+
		"\u0003\u001c\u000e\u0000\u00e0\u001b\u0001\u0000\u0000\u0000\u00e1\u00e2"+
		"\u0005&\u0000\u0000\u00e2\u001d\u0001\u0000\u0000\u0000\u0013$+BWftv}"+
		"\u0087\u008f\u0091\u00a4\u00aa\u00b4\u00bc\u00be\u00c9\u00d2\u00da";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}