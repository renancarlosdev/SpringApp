package com.renan.springapp;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.renan.springapp.domain.Categoria;
import com.renan.springapp.domain.Cidade;
import com.renan.springapp.domain.Cliente;
import com.renan.springapp.domain.Endereco;
import com.renan.springapp.domain.Estado;
import com.renan.springapp.domain.Pagamento;
import com.renan.springapp.domain.PagamentoComBoleto;
import com.renan.springapp.domain.PagamentoComCartao;
import com.renan.springapp.domain.Pedido;
import com.renan.springapp.domain.Produto;
import com.renan.springapp.domain.enums.EstadoPagamento;
import com.renan.springapp.domain.enums.TipoCliente;
import com.renan.springapp.repositories.CategoriaRepository;
import com.renan.springapp.repositories.CidadeRepository;
import com.renan.springapp.repositories.ClienteRepository;
import com.renan.springapp.repositories.EnderecoRepository;
import com.renan.springapp.repositories.EstadoRepository;
import com.renan.springapp.repositories.PagamentoRepository;
import com.renan.springapp.repositories.PedidoRepository;
import com.renan.springapp.repositories.ProdutoRepository;

@SpringBootApplication
public class AppExercicioApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository catRepo;
	@Autowired
	private ProdutoRepository prodRepo;
	@Autowired
	private EstadoRepository estRepo;
	@Autowired
	private CidadeRepository cidRepo;
	@Autowired
	private ClienteRepository cliRepo;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private PedidoRepository pedRepo;
	@Autowired
	private PagamentoRepository pagRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(AppExercicioApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));		
		
		catRepo.saveAll(Arrays.asList(cat1,cat2));
		prodRepo.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estRepo.saveAll(Arrays.asList(est1,est2));
		cidRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva", "maria@email.com","456465456",TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("4564654465","4564656"));
		
		Endereco e1 = new Endereco(null,"Rua FLores","300","apt 303", "Jardim", "450245646",cli1,c1);
		Endereco e2 = new Endereco(null, "av matos","300","sala 1","Centro","456465",cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		cliRepo.saveAll(Arrays.asList(cli1));
		endRepo.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"),cli1,e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedRepo.saveAll(Arrays.asList(ped1,ped2));
		pagRepo.saveAll(Arrays.asList(pagto1,pagto2));
	}

}
