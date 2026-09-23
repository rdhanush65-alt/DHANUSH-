const products=[
{id:1,name:"Premium Rice",category:"Groceries",price:60,stock:42,demand:35,icon:"🍚"},
{id:2,name:"Fresh Milk",category:"Dairy",price:30,stock:18,demand:30,icon:"🥛"},
{id:3,name:"Wheat Biscuits",category:"Snacks",price:20,stock:65,demand:42,icon:"🍪"},
{id:4,name:"Cooking Oil",category:"Groceries",price:145,stock:12,demand:28,icon:"🫗"},
{id:5,name:"Apple",category:"Fruits",price:120,stock:25,demand:20,icon:"🍎"},
{id:6,name:"Banana",category:"Fruits",price:50,stock:30,demand:26,icon:"🍌"},
{id:7,name:"Bread",category:"Bakery",price:45,stock:10,demand:22,icon:"🍞"},
{id:8,name:"Coffee",category:"Beverages",price:180,stock:20,demand:18,icon:"☕"}
];
let cart=[], activeCategory="All";
const money=n=>new Intl.NumberFormat("en-IN").format(n);

function renderCategories(){
 const cats=["All",...new Set(products.map(p=>p.category))];
 categoryBar.innerHTML=cats.map(c=>`<button class="${c===activeCategory?"active":""}" onclick="setCategory('${c}')">${c}</button>`).join("");
}
function setCategory(c){activeCategory=c;renderCategories();renderProducts();}
function renderProducts(){
 const q=search.value.toLowerCase();
 const list=products.filter(p=>(activeCategory==="All"||p.category===activeCategory)&&p.name.toLowerCase().includes(q));
 productGrid.innerHTML=list.map(p=>`<article class="product"><div class="product-icon">${p.icon}</div><h3>${p.name}</h3><div class="muted">${p.category}</div><div class="price">₹${money(p.price)}</div><button onclick="addToCart(${p.id})">Add to Cart</button></article>`).join("")||"<p>No products found.</p>";
}
function addToCart(id){const p=products.find(x=>x.id===id);const item=cart.find(x=>x.id===id);item?item.qty++:cart.push({...p,qty:1});renderCart();renderRecommendations();openCart();}
function renderCart(){
 cartCount.textContent=cart.reduce((s,x)=>s+x.qty,0);
 cartItems.innerHTML=cart.length?cart.map(x=>`<div class="cart-item"><span>${x.icon} ${x.name} × ${x.qty}</span><b>₹${money(x.price*x.qty)}</b></div>`).join(""):"<p>Your cart is empty.</p>";
 cartTotal.textContent=money(cart.reduce((s,x)=>s+x.price*x.qty,0));
}
function renderRecommendations(){
 const cats=new Set(cart.map(x=>x.category));
 let rec=products.filter(p=>!cart.some(x=>x.id===p.id)&&cats.has(p.category)).slice(0,4);
 if(!rec.length)rec=products.filter(p=>!cart.some(x=>x.id===p.id)).sort((a,b)=>b.demand-a.demand).slice(0,4);
 recommendations.innerHTML=rec.map(p=>`<div class="rec"><div>${p.icon}</div><b>${p.name}</b><p>₹${money(p.price)} • AI suggested</p><button onclick="addToCart(${p.id})">Add</button></div>`).join("");
 heroRecommendation.textContent=rec[0]?`Try ${rec[0].name} based on popular shopping patterns.`:"Add products to get recommendations.";
}
function openCart(){cartPanel.classList.add("open");overlay.classList.add("show")}
function closeCart(){cartPanel.classList.remove("open");overlay.classList.remove("show")}
function renderDashboard(){
 const totalStock=products.reduce((s,p)=>s+p.stock,0);
 const totalDemand=products.reduce((s,p)=>s+p.demand,0);
 stats.innerHTML=`<div class="stat"><div>Total Products</div><b>${products.length}</b></div><div class="stat"><div>Current Stock</div><b>${totalStock}</b></div><div class="stat"><div>Predicted Demand</div><b>${totalDemand}</b></div>`;
 inventoryTable.innerHTML=products.map(p=>{const low=p.stock<p.demand;return `<tr><td>${p.icon} ${p.name}</td><td>${p.stock}</td><td>${p.demand}</td><td class="${low?"low":"ok"}">${low?"Restock soon":"Healthy"}</td></tr>`}).join("");
}
search.addEventListener("input",renderProducts);
cartBtn.onclick=openCart;closeCartBtn=closeCart;document.getElementById("closeCart").onclick=closeCart;overlay.onclick=closeCart;
checkout.onclick=()=>{if(!cart.length)return alert("Your cart is empty.");alert("Order placed successfully! Thank you for shopping with PocketMarts.");cart=[];renderCart();renderRecommendations();closeCart()};
renderCategories();renderProducts();renderCart();renderRecommendations();renderDashboard();
