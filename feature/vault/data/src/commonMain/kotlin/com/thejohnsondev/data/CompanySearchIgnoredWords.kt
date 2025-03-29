package com.thejohnsondev.data

object CompanySearchIgnoredWords {
    fun getList(): Set<String> = setOf(
        // General/common words
        "main", "account", "my", "personal", "login", "password", "email", "user", "username",
        "profile", "dashboard", "portal", "app", "service", "website", "web", "online", "site",
        "secure", "access", "auth", "authentication", "security", "credentials", "backup", "sync",
        "recovery", "vault", "verification", "validation", "support", "identity", "data", "storage",
        "cloud", "privacy", "protection",

        // Work & Business-related
        "work", "business", "company", "corporate", "enterprise", "office", "professional",
        "team", "client", "partner", "vendor", "supplier", "service", "subscription", "billing",
        "invoice", "contract", "license", "crm", "hr", "payroll", "meeting", "conference",
        "admin", "management", "operations", "projects", "task", "workflow", "board",

        // Finance & Banking
        "bank", "banking", "card", "credit", "debit", "loan", "mortgage", "insurance",
        "investment", "trading", "stock", "crypto", "wallet", "payment", "checkout",
        "billing", "transaction", "invoice", "tax", "finance", "money", "wealth",
        "pension", "savings", "fund", "budget", "deposit", "withdrawal",

        // Personal & Family
        "family", "kids", "children", "child", "school", "teacher", "student", "class",
        "education", "learning", "home", "house", "apartment", "rent", "mortgage",
        "electricity", "water", "gas", "internet", "utilities", "provider", "subscription",
        "membership", "club", "hobby", "sport", "fitness", "health", "doctor", "hospital",
        "medical", "insurance", "pharmacy", "prescription",

        // Social Media & Entertainment
        "social", "media", "network", "community", "forum", "blog", "post", "comment",
        "friends", "followers", "profile", "chat", "messages", "streaming", "video",
        "music", "tv", "radio", "podcast", "news", "magazine", "newspaper", "content",
        "watch", "listen", "play", "gaming", "e-sports", "competition",

        // E-commerce & Shopping (Without company names)
        "shopping", "store", "shop", "cart", "checkout", "order", "purchase", "sale",
        "discount", "coupon", "promo", "gift", "voucher", "wishlist", "product",
        "seller", "buyer", "shipping", "delivery", "return", "refund", "exchange",
        "marketplace", "wholesale",

        // Travel & Transport
        "travel", "trip", "vacation", "holiday", "flight", "hotel", "booking",
        "reservation", "ticket", "tour", "car", "rental", "bus", "train", "subway",
        "transport", "commute", "ride", "bike", "scooter",
        "parking", "toll", "passport", "border", "immigration",

        // Government & Official Services
        "government", "official", "public", "citizen", "id", "passport",
        "tax", "revenue", "license", "permit", "census", "registry", "vote",
        "elections", "court", "law", "attorney", "justice", "police", "military",
        "embassy", "consulate", "municipality", "council", "parliament", "senate",
        "president", "prime", "minister",

        // Technology & IT Services
        "software", "hardware", "tech", "device", "smartphone", "tablet", "computer",
        "desktop", "laptop", "network", "server", "database", "developer", "coding",
        "programming", "api", "framework", "platform", "mobile", "cloud", "storage",
        "hosting", "domain", "dns", "support", "it", "helpdesk", "troubleshoot",
        "debug", "update", "patch", "version", "release", "test", "qa",

        // Miscellaneous
        "premium", "basic", "trial", "free", "standard", "gold", "silver", "platinum",
        "deluxe", "plus", "extra", "exclusive", "early", "access", "demo", "beta",
        "subscription", "renewal", "expired", "cancel", "unsubscribe"
    )
}